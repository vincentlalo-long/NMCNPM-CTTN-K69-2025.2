package com.kstn.group4.backend.venue.service.admin;

import com.kstn.group4.backend.exception.BusinessException;
import com.kstn.group4.backend.exception.ResourceNotFoundException;
import com.kstn.group4.backend.venue.dto.admin.PitchDetailResponse;
import com.kstn.group4.backend.venue.entity.Pitch;
import com.kstn.group4.backend.venue.entity.PriceRule;
import com.kstn.group4.backend.venue.entity.Venue;
import com.kstn.group4.backend.venue.repository.PitchRepository;
import com.kstn.group4.backend.venue.repository.PriceRuleRepository;
import com.kstn.group4.backend.venue.repository.VenueRepository;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PitchService {

    private static final int MIN_SLOT = 1;
    private static final int MAX_SLOT = 11;

    private final PitchRepository pitchRepository;
    private final VenueRepository venueRepository;
    private final PriceRuleRepository priceRuleRepository;

        @Transactional(readOnly = true)
        public Page<PitchDetailResponse> getPitchesByVenueForManager(Integer venueId, Integer managerId, Pageable pageable) {
        Venue venue = venueRepository.findByIdAndManagerId(venueId, managerId)
            .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy cụm sân với ID: " + venueId, "Venue"));

        return pitchRepository.findByVenueIdAndVenueManagerId(venue.getId(), managerId, pageable)
            .map(pitch -> getPitchDetailForManager(pitch.getId(), managerId));
        }

        @Transactional(readOnly = true)
        public PitchDetailResponse getPitchDetailForManager(Integer pitchId, Integer managerId) {
        Pitch pitch = pitchRepository.findByIdAndVenueManagerId(pitchId, managerId)
            .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sân với ID: " + pitchId, "Pitch"));

        List<PriceRule> rules = priceRuleRepository.findByPitchIdOrderBySlotNumberAscIsWeekendAsc(pitch.getId());

        return new PitchDetailResponse(
            pitch.getId(),
            pitch.getName(),
            pitch.getPitchType(),
            pitch.getIsActive(),
            pitch.getVenue() != null ? pitch.getVenue().getId() : null,
            pitch.getVenue() != null ? pitch.getVenue().getName() : null,
            pitch.getBasePrice(),
            toSlotPriceTable(rules)
        );
        }

        public PitchDetailResponse addPitchToVenueForManager(Integer venueId, Pitch request, Integer managerId) {
        Venue venue = venueRepository.findByIdAndManagerId(venueId, managerId)
            .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy cụm sân với ID: " + venueId, "Venue"));

        Pitch pitch = new Pitch();
        pitch.setName(request.getName());
        pitch.setPitchType(request.getPitchType());
        pitch.setIsActive(request.getIsActive() != null ? request.getIsActive() : Boolean.TRUE);
        pitch.setBasePrice(request.getBasePrice());
        pitch.setVenue(venue);

        Pitch savedPitch = pitchRepository.save(pitch);
        return getPitchDetailForManager(savedPitch.getId(), managerId);
        }

        public PitchDetailResponse updatePitchForManager(Integer pitchId, Pitch request, Integer managerId) {
        Pitch pitch = pitchRepository.findByIdAndVenueManagerId(pitchId, managerId)
            .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sân với ID: " + pitchId, "Pitch"));

        pitch.setName(request.getName());
        pitch.setPitchType(request.getPitchType());
        pitch.setBasePrice(request.getBasePrice());
        pitch.setIsActive(request.getIsActive() != null ? request.getIsActive() : pitch.getIsActive());

        Pitch updated = pitchRepository.save(pitch);
        return getPitchDetailForManager(updated.getId(), managerId);
        }

        public void deletePitchForManager(Integer pitchId, Integer managerId) {
        Pitch pitch = pitchRepository.findByIdAndVenueManagerId(pitchId, managerId)
            .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sân với ID: " + pitchId, "Pitch"));
        pitchRepository.delete(pitch);
        }

    public PitchDetailResponse addPitchToVenue(Integer venueId, Pitch request) {
        Venue venue = venueRepository.findById(venueId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy cụm sân với ID: " + venueId, "Venue"));

        Pitch pitch = new Pitch();
        pitch.setName(request.getName());
        pitch.setPitchType(request.getPitchType());
        pitch.setIsActive(request.getIsActive() != null ? request.getIsActive() : Boolean.TRUE);
        pitch.setBasePrice(request.getBasePrice());
        pitch.setVenue(venue);

        Pitch savedPitch = pitchRepository.save(pitch);
        return getPitchDetail(savedPitch.getId());
    }

    @Transactional(readOnly = true)
    public BigDecimal calculatePrice(Integer pitchId, Integer slotNumber, LocalDate date) {
        validateSlotNumber(slotNumber);

        if (date == null) {
            throw new BusinessException("Ngày thi đấu không được để trống", "INVALID_DATE");
        }

        if (!pitchRepository.existsById(pitchId)) {
            throw new ResourceNotFoundException("Không tìm thấy sân với ID: " + pitchId, "Pitch");
        }

        boolean isWeekend = isWeekend(date);

        return priceRuleRepository.findByPitchIdAndSlotNumberAndIsWeekend(pitchId, slotNumber, isWeekend)
                .map(PriceRule::getPrice)
                .orElseThrow(() -> new BusinessException(
                        "Không tìm thấy luật giá cho sân " + pitchId + ", ca " + slotNumber + ", ngày " + (isWeekend ? "cuối tuần" : "thường"),
                        "PRICE_RULE_NOT_FOUND"
                ));
    }

    @Transactional(readOnly = true)
    public PitchDetailResponse getPitchDetail(Integer pitchId) {
        Pitch pitch = pitchRepository.findByIdWithDetails(pitchId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sân với ID: " + pitchId, "Pitch"));

        List<PriceRule> rules = priceRuleRepository.findByPitchIdOrderBySlotNumberAscIsWeekendAsc(pitchId);

        return new PitchDetailResponse(
                pitch.getId(),
                pitch.getName(),
                pitch.getPitchType(),
                pitch.getIsActive(),
                pitch.getVenue() != null ? pitch.getVenue().getId() : null,
                pitch.getVenue() != null ? pitch.getVenue().getName() : null,
                pitch.getBasePrice(),
                toSlotPriceTable(rules)
        );
    }

    private List<PitchDetailResponse.SlotPriceResponse> toSlotPriceTable(List<PriceRule> rules) {
        List<PriceRule> safeRules = rules == null ? List.of() : rules.stream()
                .sorted(Comparator.comparing(PriceRule::getSlotNumber).thenComparing(PriceRule::getIsWeekend))
                .toList();

        List<PitchDetailResponse.SlotPriceResponse> table = new ArrayList<>();
        for (int slot = MIN_SLOT; slot <= MAX_SLOT; slot++) {
            
            // FIX LỖI Ở ĐÂY: Tạo một biến effectively final
            final int currentSlot = slot; 

            BigDecimal weekdayPrice = safeRules.stream()
                    .filter(rule -> currentSlot == rule.getSlotNumber() && Boolean.FALSE.equals(rule.getIsWeekend()))
                    .map(PriceRule::getPrice)
                    .findFirst()
                    .orElse(null);

            BigDecimal weekendPrice = safeRules.stream()
                    .filter(rule -> currentSlot == rule.getSlotNumber() && Boolean.TRUE.equals(rule.getIsWeekend()))
                    .map(PriceRule::getPrice)
                    .findFirst()
                    .orElse(null);

            table.add(new PitchDetailResponse.SlotPriceResponse(currentSlot, weekdayPrice, weekendPrice));
        }
        return table;
    }

    private void validateSlotNumber(Integer slotNumber) {
        if (slotNumber == null || slotNumber < MIN_SLOT || slotNumber > MAX_SLOT) {
            throw new BusinessException("slotNumber phải nằm trong khoảng 1-11", "INVALID_SLOT_NUMBER");
        }
    }

    private boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }
}