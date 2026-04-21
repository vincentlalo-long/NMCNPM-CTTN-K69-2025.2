package com.kstn.group4.backend.controller.player;

import com.kstn.group4.backend.dto.player.CreatePitchReviewRequest;
import com.kstn.group4.backend.dto.player.PitchReviewResponse;
import com.kstn.group4.backend.service.player.PlayerReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pitches/{pitchId}/reviews")
public class PlayerReviewController {

    private final PlayerReviewService playerReviewService;

    @PostMapping
    @PreAuthorize("hasRole('PLAYER')")
    public ResponseEntity<PitchReviewResponse> createReview(
            @PathVariable("pitchId") Integer pitchId,
            @Valid @RequestBody CreatePitchReviewRequest request
    ) {
        PitchReviewResponse response = playerReviewService.createReview(pitchId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

