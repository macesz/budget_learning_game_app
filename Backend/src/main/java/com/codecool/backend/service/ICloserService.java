package com.codecool.backend.service;

import com.codecool.backend.model.entity.Closer;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
@Service
public interface ICloserService {

    Closer getLastCloser(Long householdId, LocalDate balanceDate);
    Closer createCloser(Long householdId, LocalDate closerDate);
}
