package com.codecool.backend.service;

import com.codecool.backend.model.entity.Pet;
import com.codecool.backend.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetService {
    @Autowired
     private PetRepository petRepository;

    public void rewordPet(Long userId, int xpAmount){
        Pet pet = petRepository.findByOwnerId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found"));

        pet.addXp(xpAmount);

        petRepository.save(pet);
    }
}
