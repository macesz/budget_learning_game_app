package com.codecool.backend.controller.dto;

import com.codecool.backend.model.entity.Household;

public record HouseHoldDto(Long id) {
    public HouseHoldDto(Household household){
        this(household.getId());
    }
}
