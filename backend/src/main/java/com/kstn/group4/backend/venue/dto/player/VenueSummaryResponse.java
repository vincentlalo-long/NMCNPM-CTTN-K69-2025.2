package com.kstn.group4.backend.venue.dto.player;

import java.math.BigDecimal;
import java.time.LocalTime;

public record VenueSummaryResponse(
	Integer id,
	String name,
	String address,
	LocalTime openTime,
	LocalTime closeTime,
	BigDecimal minPrice
) {
}
