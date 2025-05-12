package com.codecool.backend.repository.pokemon;

import com.codecool.backend.model.pokemon.UserPokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPokemonRepository extends JpaRepository<UserPokemon, Long> {

    @Query("SELECT up FROM UserPokemon up JOIN PokemonSpecies ps ON ps.name = up.species.name " +
            "WHERE ps.evolutionTrigger = 'happiness' AND up.happiness >= ps.evolutionThreshold")
    Optional<List<UserPokemon>> findPokemonReadyToEvolveByHappiness();

    List<UserPokemon> findAllByUserId(Long userId);

    Optional<UserPokemon> findFirstByUserIdOrderByHatchDateDesc(Long userId);

}
