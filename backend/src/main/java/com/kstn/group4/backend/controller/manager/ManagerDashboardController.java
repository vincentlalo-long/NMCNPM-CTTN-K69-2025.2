package com.kstn.group4.backend.controller.manager;

import com.kstn.group4.backend.dto.manager.RevenueResponse;
import com.kstn.group4.backend.service.common.AuthenticatedUserService;
import com.kstn.group4.backend.service.manager.ManagerDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/managers/dashboard")
public class ManagerDashboardController {

    private final ManagerDashboardService managerDashboardService;
    private final AuthenticatedUserService authenticatedUserService;

    @GetMapping("/revenue")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<RevenueResponse> getRevenue() {
        Integer ownerId = authenticatedUserService.getCurrentUserId();
        return ResponseEntity.ok(managerDashboardService.getRevenue(ownerId));
    }
}

