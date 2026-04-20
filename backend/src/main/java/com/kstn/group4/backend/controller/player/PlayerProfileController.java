package com.kstn.group4.backend.controller.player;

import com.kstn.group4.backend.dto.player.UpdateUserProfileRequest;
import com.kstn.group4.backend.dto.player.UserProfileResponse;
import com.kstn.group4.backend.service.player.PlayerProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class PlayerProfileController {

    private final PlayerProfileService playerProfileService;

    @GetMapping("/me")
    @PreAuthorize("hasRole('PLAYER')")
    public ResponseEntity<UserProfileResponse> getMyProfile() {
        return ResponseEntity.ok(playerProfileService.getCurrentProfile());
    }

    @PutMapping("/me")
    @PreAuthorize("hasRole('PLAYER')")
    public ResponseEntity<UserProfileResponse> updateMyProfile(@Valid @RequestBody UpdateUserProfileRequest request) {
        return ResponseEntity.ok(playerProfileService.updateCurrentProfile(request));
    }
}

