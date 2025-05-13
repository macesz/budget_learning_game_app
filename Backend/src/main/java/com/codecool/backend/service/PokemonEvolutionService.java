package com.codecool.backend.service;

import com.codecool.backend.controller.dto.UserPokemonDto;
import com.codecool.backend.controller.exception.PokemonNotFoundException;
import com.codecool.backend.model.pokemon.EvolutionChain;
import com.codecool.backend.model.pokemon.PokemonAsset;
import com.codecool.backend.model.pokemon.PokemonSpecies;
import com.codecool.backend.model.pokemon.UserPokemon;
import com.codecool.backend.repository.pokemon.EvolutionChainRepository;
import com.codecool.backend.repository.pokemon.PokemonAssetRepository;
import com.codecool.backend.repository.pokemon.PokemonSpeciesRepository;
import com.codecool.backend.repository.pokemon.UserPokemonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokemonEvolutionService {

    private UserPokemonRepository userPokemonRepository;

    private EvolutionChainRepository evolutionChainRepository;

    private PokemonSpeciesRepository pokemonSpeciesRepository;

    private PokemonAssetRepository pokemonAssetRepository;

    @Autowired
    public PokemonEvolutionService(
            UserPokemonRepository userPokemonRepository,
            EvolutionChainRepository evolutionChainRepository,
            PokemonSpeciesRepository pokemonSpeciesRepository,
            PokemonAssetRepository pokemonAssetRepository) {
        this.userPokemonRepository = userPokemonRepository;
        this.evolutionChainRepository = evolutionChainRepository;
        this.pokemonSpeciesRepository = pokemonSpeciesRepository;
        this.pokemonAssetRepository = pokemonAssetRepository;
    }


//    public List<UserPokemonDto> checkHappinessEvolutions() {
//        List<UserPokemon> pokemons = userPokemonRepository.findPokemonReadyToEvolveByHappiness()
//                .orElseThrow(() -> new PokemonNotFoundException("No Pokemon ready to evolve "));
//        return pokemons.stream()
//                .map(UserPokemonDto::new)
//                .toList();
//    }
//
//    /**
//     * Mark a Pokémon as ready to evolve
//     */
//    @Transactional
//    public boolean markForEvolution(Long pokemonId) {
//        UserPokemon pokemon = userPokemonRepository.findById(pokemonId)
//                .orElseThrow(() -> new PokemonNotFoundException("Pokemon not found"));
//
//        pokemon.setIsEvolutionPending(true);
//        userPokemonRepository.save(pokemon);
//        return true;
//    }
//
//    @Transactional
//    public UserPokemonDto evolvePokemon(Long pokemonId) {
//        UserPokemon pokemon = userPokemonRepository.findById(pokemonId)
//                .orElseThrow(() -> new PokemonNotFoundException("Pokemon not found"));
//
//            if (!pokemon.getIsEvolutionPending()) {
//                throw new IllegalStateException("Pokémon is not ready to evolve");
//            }
////TODO
//            List<EvolutionChain> evolutions = evolutionChainRepository.findEvolutionChainBySpeciesId()
//                    .orElseThrow(() -> new PokemonNotFoundException("Evolutions not found"));
//
//            if (evolutions.isEmpty()) {
//                pokemon.setIsEvolutionPending(false);
//                UserPokemon userPokemon = userPokemonRepository.save(pokemon);
//                return new UserPokemonDto(userPokemon);
//            }
//
//            EvolutionChain correctEvolution = null;
//
//            for (EvolutionChain evolution : evolutions) {
//                // Since all our evolutions are happiness-based with the same threshold (220),
//                // we can just take the first one. In a more complex system with multiple
//                // evolution paths, we'd need more logic here.
//                correctEvolution = evolution;
//                break;
//            }
//
//            if (correctEvolution == null) {
//                throw new IllegalStateException("No suitable evolution found for this Pokémon");
//            }
//
//            PokemonSpecies evolvedSpecies = pokemonSpeciesRepository.findById(pokemon.getSpecies().getId()).orElseThrow(() -> new PokemonNotFoundException("Evolved species not found"));
//
//
//            PokemonAsset evolvedAsset = pokemonAssetRepository.findBySpeciesId(correctEvolution.getCurrentEvolution(0).getId())
//                    .orElseThrow(() -> new PokemonNotFoundException("Assets for evolved form not found"));
//
//
//            pokemon.setSpecies(evolvedSpecies);
//            pokemon.setPictureUrl(evolvedAsset.getPictureUrl());
//            pokemon.setGifUrl(evolvedAsset.getGifUrl());
//
//
//            pokemon.setHappiness(evolvedSpecies.getBaseHappiness());
//            pokemon.setIsEvolutionPending(false);
//
//            UserPokemon userPokemon = userPokemonRepository.save(pokemon);
//            return new UserPokemonDto(userPokemon);
//    }

//    // TODO implement logic
//    @Transactional
//    public UserPokemonDto increaseHappiness(Long pokemonId, Integer amount) {
//        UserPokemon pokemon = userPokemonRepository.findById(pokemonId)
//                .orElseThrow(() -> new PokemonNotFoundException("Pokemon not found"));
//
//        pokemon.setHappiness(Math.min(255, pokemon.getHappiness() + amount));
//        UserPokemon userPokemon = userPokemonRepository.save(pokemon);
//        return new UserPokemonDto(userPokemon);
//    }
//
//    public List<UserPokemonDto> getAllPokemonsByUserId(Long userId) {
//        List<UserPokemon> pokemons = userPokemonRepository.findAllByUserId(userId);
//        return pokemons.stream()
//                .map(UserPokemonDto::new)
//                .collect(Collectors.toList());
//    }
//
//    public UserPokemonDto getCurrentPokemonById(Long userId) {
//        UserPokemon pokemon = userPokemonRepository.findFirstByUserIdOrderByHatchDateDesc(userId).orElseThrow(
//                () -> new PokemonNotFoundException("Pokemon not found")
//        );
//        return new UserPokemonDto(pokemon);
//    }

}
