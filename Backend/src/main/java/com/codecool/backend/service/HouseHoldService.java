package com.codecool.backend.service;

import com.codecool.backend.model.entity.Household;
import com.codecool.backend.repository.HouseholdRepository;
import com.codecool.backend.repository.TransactionRepository;
import com.codecool.backend.repository.UserEntityRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class HouseHoldService {

    private final HouseholdRepository householdRepository;

    public HouseHoldService(HouseholdRepository householdRepository) {

        this.householdRepository = householdRepository;
    }

    public Optional<Household> findHouseholdById(Long id){
        return householdRepository.findById(id);
    }

    public boolean deleteHouseholdById(Long id){
        return householdRepository.deleteHouseholdById(id);
    }

    public Household createHousehold(Long householdId) {
        return householdRepository.save(new Household(householdId));
    }
}
