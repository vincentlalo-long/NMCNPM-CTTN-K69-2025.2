package com.kstn.group4.backend.service.manager;

import com.kstn.group4.backend.dto.manager.RevenueResponse;
import com.kstn.group4.backend.service.common.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ManagerDashboardService {

    private final BookingService bookingService;

    @Transactional(readOnly = true)
    public RevenueResponse getRevenue(Integer ownerId) {
        return bookingService.getOwnerRevenue(ownerId);
    }
}


