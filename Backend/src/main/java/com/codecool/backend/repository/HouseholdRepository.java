package com.codecool.backend.repository;

import com.codecool.backend.model.entity.Household;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HouseholdRepository extends JpaRepository<Household, Long> {
    boolean deleteHouseholdById(Long id);
     Optional<Household> findHouseholdById(Long id);

}
