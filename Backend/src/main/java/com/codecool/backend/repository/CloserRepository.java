package com.codecool.backend.repository;

import com.codecool.backend.model.entity.Closer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CloserRepository extends JpaRepository<Closer, Long> {
    List<Closer> findAllById(Long userId);
    Closer findFirstByHouseholdIdAndDateLessThanEqualOrderByDateDesc(Long householdId, LocalDate balanceDate);
    List<Closer> findByHouseholdIdOrderByDateDesc(Long householdId);

}
