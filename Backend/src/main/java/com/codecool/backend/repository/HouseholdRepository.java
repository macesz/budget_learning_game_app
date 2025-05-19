package com.codecool.backend.repository;

import com.codecool.backend.model.entity.Household;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HouseholdRepository extends JpaRepository<Household, Long> {
    boolean deleteHouseholdById(Long id);
    Optional<Household> findHouseholdById(Long id);

}
