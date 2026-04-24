package com.kstn.group4.backend.venue.repository;

import com.kstn.group4.backend.venue.entity.AddonService;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddonServiceRepository extends JpaRepository<AddonService, Integer> {
    List<AddonService> findByPitchId(Integer pitchId);
}