package com.codecool.backend.repository;

import com.codecool.backend.model.entity.Closer;
import com.codecool.backend.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CloserRepository extends JpaRepository<Closer, Long> {
    List<Closer> findAllById(Long userId);
    Closer getCloserByDateAndId(Long householdId, LocalDate balanceDate);
    List<Closer> getCloserByHouseholdId(Long householdId);

}
