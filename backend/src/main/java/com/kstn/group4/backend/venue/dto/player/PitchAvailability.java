package com.kstn.group4.backend.venue.dto.player;

import com.kstn.group4.backend.venue.entity.PitchType;
import java.util.List;

public record PitchAvailability(
        Integer pitchId,
        String pitchName,
        PitchType pitchType,
        List<SlotStatus> slots
) {
}