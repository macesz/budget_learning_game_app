package com.codecool.backend.service;

import com.codecool.backend.controller.dto.pokemon.*;
import com.codecool.backend.controller.exception.PokemonNotFoundException;
import com.codecool.backend.model.pokemon.*;
import com.codecool.backend.repository.pokemon.EvolutionChainRepository;
import com.codecool.backend.repository.pokemon.PokemonAssetRepository;
import com.codecool.backend.repository.pokemon.PokemonSpeciesRepository;
import com.codecool.backend.repository.pokemon.UserPokemonRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@Service
public class PokemonService {
    private final PokemonAssetRepository pokemonAssetRepository;
    private final EvolutionChainRepository evolutionChainRepository;
    private final RestTemplate restTemplate;
    private final UserPokemonRepository userPokemonRepository;
    private final PokemonSpeciesRepository pokemonSpeciesRepository;

    @Autowired
    public PokemonService(PokemonAssetRepository pokemonAssetRepository,
                          EvolutionChainRepository evolutionChainRepository,
                          UserPokemonRepository userPokemonRepository,
                          PokemonSpeciesRepository pokemonSpeciesRepository,
                          RestTemplate restTemplate) {
        this.pokemonAssetRepository = pokemonAssetRepository;
        this.evolutionChainRepository = evolutionChainRepository;
        this.userPokemonRepository = userPokemonRepository;
        this.pokemonSpeciesRepository = pokemonSpeciesRepository;
        this.restTemplate = restTemplate;

    }

    //@PostConstruct
//    @Transactional
//    @EventListener(ApplicationReadyEvent.class)
//    public void init() {
//        if (evolutionChainRepository.count() == 0) {
//            fetchEvolutionChains(100);
//        }
//    }

//    private void fetchEvolutionChains(Integer num) {
//        for (Integer id = 1; id <= num; id++) {
//            try {
//                String url = getFormattedUrl(String.valueOf(id), PokeApiBaseUrl.EVOLUTION);
//                EvolutionChainDto response = restTemplate.getForObject(url, EvolutionChainDto.class);
//
//                if (response != null && response.chain() != null) {
//                    log.info("About to access evolutionIds of chain ID: {}", response.chain());
//
//                 List<PokemonSpecies> speciesList = parseEvolutionChain(id, response.chain());
//                    fetchAndSaveSpecies(speciesList);
//                }
//            } catch (Exception e) {
//                System.err.println("Error fetching evolution chain " + id + ": " + e.getMessage());
//            }
//        }
//    }

    private List<PokemonSpecies> parseEvolutionChain(int chainId, EvolutionStageDto rootStage) {
        EvolutionChain evolutionChain = new EvolutionChain();
        evolutionChain.setId(chainId);

        List<PokemonSpecies> speciesList = new ArrayList<>();
        EvolutionStageDto currentStage = rootStage;

        while (currentStage != null) {
            NamedPokeApiResourceDto speciesDto = currentStage.species();
            if (speciesDto != null) {
                Integer speciesId = getIdFromUrl(speciesDto.url());
                String name = speciesDto.name();

                PokemonSpecies species = new PokemonSpecies();
                species.setId(speciesId);
                species.setName(name);
                species.setEvolutionChain(evolutionChain);

                speciesList.add(species);
            }
            List<EvolutionStageDto> evolvesTo = currentStage.evolvesTo();
            currentStage = (evolvesTo != null && !evolvesTo.isEmpty()) ? evolvesTo.get(0) : null;
        }
        evolutionChain.setEvolutions(speciesList);
        evolutionChainRepository.save(evolutionChain);
        return speciesList;
    }

//    public void fetchAndSaveSpecies(List<PokemonSpecies> speciesList) {
//        for (PokemonSpecies species : speciesList) {
//            try {
//                String url = getFormattedUrl(String.valueOf(species.getId()), PokeApiBaseUrl.SPECIES); // e.g., https://pokeapi.co/api/v2/pokemon-species/{id}
//                PokemonSpeciesDto dto = restTemplate.getForObject(url, PokemonSpeciesDto.class);
//                if (dto != null && dto.id() != null) {
//                    saveOrUpdateSpecies(dto);
//                }
//
//            } catch (Exception e) {
//                log.warn("Failed to fetch species with ID {}: {}", species.getId(), e.getMessage());
//            }
//        }
//
//    }

//    private void saveOrUpdateSpecies(PokemonSpeciesDto dto) {
//        Integer speciesId = dto.id();
//        if (speciesId == null) {
//            throw new RuntimeException("Species with ID " + speciesId + "?????????");
//        }
//
//        PokemonSpecies species = pokemonSpeciesRepository.findById(speciesId).orElseGet(PokemonSpecies::new);
//        species.setId(speciesId);
//        species.setName(dto.name());
//        species.setBaseHappiness(dto.baseHappiness());
//        species.setDescription(dto.description());
//        species.setPokeIndexNumber(dto.id());
//
//        species.setEvolutionThreshold(200); //TODO
//        species.setEvolutionTrigger("Happiness");
//        if (species.getEvolutionChain() != null) {
//            Integer evolutionChainId = species.getEvolutionChain().getId();
//
//            EvolutionChain evolutionChain = evolutionChainRepository
//                    .findById(evolutionChainId)
//                    .orElseThrow(() -> new PokemonNotFoundException("Evolution chain not found"));
//
//            species.setEvolutionChain(evolutionChain);
//        }
//        pokemonSpeciesRepository.save(species);
//
//        if (dto.varieties() != null && !dto.varieties().isEmpty()) {
//            List<NamedPokeApiResourceDto> namedPokeApiResourceDtoList= dto.varieties()
//                    .stream()
//                    .map(PokemonSpeciesVarietyDto::pokemon)
//                    .toList();
//            saveOrUpdatePokemons(namedPokeApiResourceDtoList, species);
//        }
//    }

//    private void saveOrUpdatePokemons(List<NamedPokeApiResourceDto> varieties, PokemonSpecies species) {
//        for (NamedPokeApiResourceDto variety : varieties) {
//            try {
//                String name = variety.name();
//                Integer pokemonId = getIdFromUrl(variety.url());
//
//                String url = getFormattedUrl(name, PokeApiBaseUrl.POKEMON);
//                PokemonAssetDto dto = restTemplate.getForObject(url, PokemonAssetDto.class);
//
//                if (dto != null) {
//                    Integer id = dto.id();
//                    if (id != null) {
//                        PokemonAsset pokemon = pokemonAssetRepository
//                            .findPokemonAssetById(id).orElseGet(PokemonAsset::new);
//                        //log.info("pokemon", pokemon);
//                        if (pokemon.getName() == null) {
//                            pokemon = (PokemonAsset) createPokemonAsset(id, species.getName(), species, species.getEvolutionChain(), dto.baseExperience());
//                        }
//                        species.addPokemon(pokemon);
//                        pokemonAssetRepository.save(pokemon);
//                    }
//                }
//            } catch (Exception e) {
//                log.warn("Failed to fetch/save Pokémon variety '{}': {}", variety.name(), e.getMessage());
//            }
//        }
//    }
//    private PokemonAsset createPokemonAsset(Integer pokeIndex, String name, PokemonSpecies species, EvolutionChain evolution, Integer baseExperience) {
//        if (species == null || evolution == null) {
//            System.out.println("Error creating pokemon asset");
//            return null;
//        };
//        String id = pokeIndex.toString();
//        String pictureUrl = getFormattedUrl(id, PokeApiBaseUrl.PIC_URL);
//        String gifUrl = getFormattedUrl(id, PokeApiBaseUrl.GIF_URL);
//        String spriteFront = getFormattedUrl(id, PokeApiBaseUrl.SPRITE_FRONT);
//        String spriteBack = getFormattedUrl(id, PokeApiBaseUrl.SPRITE_BACK);
//        String cryAudioUrl = getFormattedUrl(id, PokeApiBaseUrl.CRY);
//
//        PokemonAsset poke = new PokemonAsset();
//        poke.setId(pokeIndex);
//        poke.setName(name);
//
//        poke.setPictureUrl(pictureUrl);
//        poke.setGifUrl(gifUrl);
//        poke.setSpriteFront(spriteFront);
//        poke.setSpriteBack(spriteBack);
//        poke.setCryAudioUrl(cryAudioUrl);
//
//        poke.setBaseExperience(baseExperience);
//        poke.setSpecies(species);
//        poke.setEvolution(evolution);
//
//        return poke;
//    }




    private static String getFormattedUrl(String identifier, PokeApiBaseUrl apiBaseUrl) {
        return apiBaseUrl.getUrl() + identifier + apiBaseUrl.getPathEnd();
    }
    private static Integer getIdFromUrl(String url) {
            String[]split = url.split("/");
            return Integer.parseInt(split[split.length - 1]);
    }


//TODO?
//    public List<UserPokemon> getUserPokemons() {
//        return userPokemonRepository.findAll();
//    }
//    public List<EvolutionChain> getAllEvolutionChains() {
//        return evolutionChainRepository.findAll();
//    }
//    public void addUserPokemon(UserPokemon pokemon) {
//        userPokemonRepository.save(pokemon);
//    }
}