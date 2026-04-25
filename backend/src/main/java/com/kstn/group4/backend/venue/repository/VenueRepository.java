package com.kstn.group4.backend.venue.repository;

import com.kstn.group4.backend.venue.entity.Venue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VenueRepository extends JpaRepository<Venue, Integer> {

    @Query("SELECT DISTINCT v FROM Venue v LEFT JOIN FETCH v.pitches WHERE v.id = :id")
    Optional<Venue> findByIdWithPitches(@Param("id") Integer id);

    @Query("SELECT DISTINCT v FROM Venue v LEFT JOIN FETCH v.pitches")
    List<Venue> findAllWithPitches();

        @Query(
            value = "SELECT DISTINCT v FROM Venue v JOIN v.pitches p WHERE p.isActive = true ORDER BY v.id DESC",
            countQuery = "SELECT COUNT(DISTINCT v.id) FROM Venue v JOIN v.pitches p WHERE p.isActive = true"
        )
        Page<Venue> findActiveVenuesForPlayer(Pageable pageable);

        @Query(
            value = "SELECT v FROM Venue v WHERE v.managerId = :managerId ORDER BY v.id DESC",
            countQuery = "SELECT COUNT(v) FROM Venue v WHERE v.managerId = :managerId"
        )
        Page<Venue> findByManagerId(@Param("managerId") Integer managerId, Pageable pageable);

        Optional<Venue> findByIdAndManagerId(Integer id, Integer managerId);
}
