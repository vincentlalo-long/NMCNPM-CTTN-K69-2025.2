package com.kstn.group4.backend.service.player;

import com.kstn.group4.backend.dto.player.UpdateUserProfileRequest;
import com.kstn.group4.backend.dto.player.UserProfileResponse;
import com.kstn.group4.backend.entity.User;
import com.kstn.group4.backend.repository.UserRepository;
import com.kstn.group4.backend.service.common.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlayerProfileService {

    private final AuthenticatedUserService authenticatedUserService;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserProfileResponse getCurrentProfile() {
        User user = authenticatedUserService.getCurrentUser();
        return toProfileResponse(user);
    }

    @Transactional
    public UserProfileResponse updateCurrentProfile(UpdateUserProfileRequest request) {
        User user = authenticatedUserService.getCurrentUser();
        user.setUsername(request.name());
        user.setPhoneNumber(request.phoneNumber());
        user.setAvatarUrl(request.avatarUrl());

        User updatedUser = userRepository.save(user);
        return toProfileResponse(updatedUser);
    }

    private UserProfileResponse toProfileResponse(User user) {
        return new UserProfileResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getAvatarUrl(),
                user.getRole()
        );
    }
}


