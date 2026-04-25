package com.kstn.group4.backend.venue.dto.player;

import java.time.LocalTime;

public record VenueDetailResponse(
	Integer id,
	String name,
	String address,
	String description,
	LocalTime openTime,
	LocalTime closeTime
) {
}
