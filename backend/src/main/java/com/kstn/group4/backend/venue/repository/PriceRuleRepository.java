package com.kstn.group4.backend.venue.repository;

import com.kstn.group4.backend.venue.entity.PriceRule;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRuleRepository extends JpaRepository<PriceRule, Integer> {

    List<PriceRule> findByPitchId(Integer pitchId);

    Optional<PriceRule> findByPitchIdAndSlotNumberAndIsWeekend(Integer pitchId, Integer slotNumber, Boolean isWeekend);

    List<PriceRule> findByPitchIdOrderBySlotNumberAscIsWeekendAsc(Integer pitchId);
}