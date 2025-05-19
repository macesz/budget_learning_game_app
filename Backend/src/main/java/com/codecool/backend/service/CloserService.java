package com.codecool.backend.service;

import com.codecool.backend.model.entity.Closer;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CloserService  implements ICloserService {
    @Override
    public Closer getLastCloser(Long householdId, LocalDate balanceDate) {
        return null;
    }

    @Override
    public Closer createCloser(Long householdId, LocalDate closerDate) {
        return null;
    }
}
