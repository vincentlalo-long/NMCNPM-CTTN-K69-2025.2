package com.kstn.group4.backend.venue.dto.player;

import java.time.LocalDate;
import java.util.List;

public record VenueAvailabilityResponse(
	Integer venueId,
	String venueName,
	LocalDate date,
	List<PitchAvailability> pitches
) {
}
