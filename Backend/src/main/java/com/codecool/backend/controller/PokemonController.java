package com.codecool.backend.controller;

import com.codecool.backend.controller.dto.UserPokemonDto;
import com.codecool.backend.service.PokemonEvolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PokemonController {

    private final PokemonEvolutionService pokemonEvolutionService;

    @Autowired
    public PokemonController(PokemonEvolutionService pokemonEvolutionService) {
        this.pokemonEvolutionService = pokemonEvolutionService;
    }
//
//    @GetMapping("/user/{userId}/currentPokemon")
//    public UserPokemonDto getCurrentPokemon(@PathVariable Long userId) {
//        return pokemonEvolutionService.getCurrentPokemonById(userId);
//    }

//    @GetMapping("/user/{userId}/pokemons")
//    public List<UserPokemonDto> getPokemons(@PathVariable Long userId) {
//        return pokemonEvolutionService.getAllPokemonsByUserId(userId);
//    }
//
//    @GetMapping("/evolution/check/happiness")
//    public List<UserPokemonDto> checkHappinessEvolution() {
//        return pokemonEvolutionService.checkHappinessEvolutions();
//    }
//
//    @PostMapping("/{pokemonId}/evolution/evolve")
//    public UserPokemonDto evolvePokemon(@PathVariable Long pokemonId) {
//        return pokemonEvolutionService.evolvePokemon(pokemonId);
//    }
//
//    @PostMapping("/{pokemonId}/evolution/mark")
//    public boolean markForEvolution(@PathVariable Long pokemonId) {
//        return pokemonEvolutionService.markForEvolution(pokemonId);
//    }
//
//    @PostMapping("/{pokemonId}/happiness/increase/{amount}")
//    public UserPokemonDto increaseHappiness(@PathVariable Long pokemonId, @PathVariable Integer amount) {
//        return pokemonEvolutionService.increaseHappiness(pokemonId, amount);
//    }

    //TODO evolutionDTO + endP
}
