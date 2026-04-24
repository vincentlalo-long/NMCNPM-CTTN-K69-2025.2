# Entity Refactoring for Module Encapsulation

## Overview

This document describes the refactoring of core entities to enforce strict module encapsulation between `user`, `venue` (pitch), and `booking` modules.

## Changes Made

### 1. Entity Layer Refactoring

#### Pitch.java (venue module)

**Before:** Used `@ManyToOne` relationship to User manager

```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "manager_id")
private User manager;
```

**After:** Changed to simple Integer ID

```java
@Column(name = "manager_id")
private Integer managerId;
```

**Status Field Enhancement:**

- Changed `private String status;` to `private PitchStatus status;`
- Added `@Enumerated(EnumType.STRING)` annotation
- Created `PitchStatus` enum: ACTIVE, INACTIVE, MAINTENANCE, CLOSED

**OneToMany Removal:**

- Removed `@OneToMany(mappedBy = "pitch")` collections for priceRules and services
- Services now access these through repositories using pitchId queries

---

#### Booking.java (booking module)

**Before:** Direct entity relationships

```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "player_id")
private User player;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "pitch_id")
private Pitch pitch;

private String status;
```

**After:** ID-based references with enum status

```java
@Column(name = "player_id")
private Integer playerId;

@Column(name = "pitch_id")
private Integer pitchId;

@Enumerated(EnumType.STRING)
private BookingStatus status;
```

**Status Field Enhancement:**

- Created `BookingStatus` enum: PENDING, CONFIRMED, COMPLETED, CANCELLED

---

#### PriceRule.java & AddonService.java (venue module)

**Before:** Bidirectional references to Pitch

```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "pitch_id")
private Pitch pitch;
```

**After:** Simple ID references

```java
@Column(name = "pitch_id")
private Integer pitchId;
```

---

### 2. Repository Layer

Created repository interfaces to support batch operations and ID-based queries:

**UserRepository:**

- Added `List<User> findByIdIn(List<Integer> ids)` for batch fetching

**PitchRepository:**

- `findByManagerId(Integer managerId)` - Find pitches owned by a manager
- `findByIdIn(List<Integer> ids)` - Batch fetch pitches
- `searchPitches(...)` - Advanced search with filters

**PriceRuleRepository & AddonServiceRepository:**

- `findByPitchId(Integer pitchId)` - Get resources for a specific pitch
- `findByPitchIdIn(List<Integer> pitchIds)` - Batch fetch for multiple pitches

**BookingRepository:**

- `findByPlayerId(...)` - Get bookings for a player
- `findByPitchId(...)` - Get bookings for a pitch
- `findByManagerId(...)` - Get bookings for a manager's pitches
- `findByPlayerIdIn(...)` & `findByPitchIdIn(...)` - Batch operations
- `findBookedSlots(...)` - Find booked time slots for a pitch on a given date
- Aggregation queries for dashboard statistics

---

### 3. Service Layer Patterns

#### Problem: N+1 Queries

**Old Approach:** Accessing related entities through @ManyToOne relationships

```java
// Problem: Loads user for EACH booking
for (Booking b : bookings) {
    User player = b.getPlayer(); // N queries
    Pitch pitch = b.getPitch();   // N queries
}
```

**New Approach:** Batch fetching with Map lookup

```java
public List<BookingWithDetails> getBookingsWithDetails(List<Integer> bookingIds) {
    List<Booking> bookings = bookingRepository.findAllById(bookingIds);

    List<Integer> playerIds = bookings.stream()
            .map(Booking::getPlayerId)
            .distinct()
            .collect(Collectors.toList());

    // Single query for all users
    Map<Integer, User> usersMap = userRepository.findByIdIn(playerIds)
            .stream()
            .collect(Collectors.toMap(User::getId, u -> u));

    // Combine with O(1) lookups
    return bookings.stream()
            .map(b -> new BookingWithDetails(b, usersMap.get(b.getPlayerId()), ...))
            .collect(Collectors.toList());
}
```

#### Service Responsibilities

**PitchService:**

- Manages pitch lifecycle (create, update, retrieve)
- Enforces authorization: only manager can modify their pitches
- Related entities (price rules, services) accessed through separate services/repositories

**BookingService:**

- Creates and manages bookings
- Validates booking ownership before cancellation
- Provides batch operations with enriched data
- Uses batch fetching to avoid N+1 queries

**PriceRuleService & AddonServiceService:**

- Manage resources within a pitch
- Always receive pitchId when creating/updating
- Repositories support batch queries by pitchId

---

## Best Practices

### 1. Always Batch Fetch When Loading Lists

```java
// ✅ Good: Load all users in one query
List<User> users = userRepository.findByIdIn(userIds);
Map<Integer, User> map = users.stream()
    .collect(Collectors.toMap(User::getId, u -> u));

// ❌ Bad: N+1 queries
for (Integer id : userIds) {
    User user = userRepository.findById(id).get();
}
```

### 2. Extract IDs Before Batch Operations

```java
// ✅ Good
List<Integer> playerIds = bookings.stream()
    .map(Booking::getPlayerId)
    .distinct()
    .collect(Collectors.toList());

// ❌ Bad: Duplicates reduce efficiency
List<Integer> playerIds = bookings.stream()
    .map(Booking::getPlayerId)
    .collect(Collectors.toList());
```

### 3. Use Enums for Status Fields

```java
// ✅ Good: Type-safe
@Enumerated(EnumType.STRING)
private BookingStatus status;

// ❌ Bad: Runtime errors possible
private String status;
```

### 4. Service Injection for Related Data

```java
@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;      // Inject for batch ops
    private final PitchRepository pitchRepository;    // Inject for batch ops
}
```

### 5. Authorization Checks in Services

```java
@Transactional
public Booking updateBookingStatus(Integer managerId, Integer bookingId, BookingStatus status) {
    Booking booking = getBookingById(bookingId);

    // Verify ownership through pitch manager ID
    Pitch pitch = pitchRepository.findById(booking.getPitchId())
            .orElseThrow();

    if (!pitch.getManagerId().equals(managerId)) {
        throw new ForbiddenOperationException("Unauthorized");
    }

    booking.setStatus(status);
    return bookingRepository.save(booking);
}
```

---

## Module Boundaries

### User Module

- ✅ Manages user data and authentication
- ❌ Does not know about Pitch or Booking entities
- Interface: Provides UserRepository and User entity only

### Venue Module (Pitch)

- ✅ Manages pitch, price rules, and add-on services
- ✅ Holds reference to manager via managerId (not User object)
- ❌ Does not instantiate or manage User objects
- Interface: Provides PitchService, PitchRepository, and related repositories

### Booking Module

- ✅ Manages bookings and related operations
- ✅ Holds references to player and pitch via IDs only
- ✅ Injects services to fetch enriched data when needed
- ❌ Does not directly create User or Pitch objects
- Interface: Provides BookingService and BookingRepository

---

## Migration Notes

### For Existing Code Using Old References

1. **Replace direct entity access:**

   ```java
   // Old: pitch.getManager().getName()
   // New: Load user separately
   User manager = userRepository.findById(pitch.getManagerId()).get();
   String name = manager.getName();
   ```

2. **Update DTOs to not include nested objects:**
   - Only include IDs or flatten the structure
   - Load related data in controllers/services as needed

3. **Update Mappers:**
   - Map IDs to IDs, not entities to entities
   - Fetch full objects in service layer

---

## Testing Recommendations

1. **Unit Tests:** Mock repositories, test service logic with IDs
2. **Integration Tests:** Use batch operations, verify no N+1 queries
3. **Performance Tests:** Monitor query count for list operations

---

## Summary

This refactoring achieves:

- ✅ **Strict Module Encapsulation:** Modules don't directly reference each other's entities
- ✅ **Type Safety:** Enums for status instead of strings
- ✅ **Performance:** Batch fetching prevents N+1 queries
- ✅ **Maintainability:** Clear service responsibilities and data flow
- ✅ **Scalability:** ID-based references scale better than object relationships
