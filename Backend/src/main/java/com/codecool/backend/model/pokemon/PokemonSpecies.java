package com.codecool.backend.model.pokemon;

import com.codecool.backend.controller.dto.pokemon.PokemonSpeciesDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@JsonIgnoreProperties("evolutionChain")
@Table(name = "pokemon_species", uniqueConstraints = @UniqueConstraint(columnNames = {"evolution_chain_id", "poke_index_number"}))
public class PokemonSpecies {
    @Getter
    @Setter
    @Id
    private Integer id;



    @Column(name = "poke_index_number")
    private Integer pokeIndexNumber;

    @Column()
    private String name;

    @OneToMany(mappedBy = "species", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<PokemonAsset> pokemons = new ArrayList<>();

    private String description;

    @Column(name = "base_happiness")
    //private Integer baseHappiness = 70;
    private Integer baseHappiness = 70;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evolution_chain_id")
    private EvolutionChain evolutionChain;

    @Column(name = "evolution_trigger")
    private String evolutionTrigger;

    @Column(name = "evolution_threshold")
    private Integer evolutionThreshold;


    public PokemonSpecies(PokemonSpeciesDto pokemonSpeciesDto) {
        this.name = pokemonSpeciesDto.name();
        this.id = pokemonSpeciesDto.id();
        this.baseHappiness = pokemonSpeciesDto.baseHappiness();
        this.pokeIndexNumber = pokemonSpeciesDto.id();
    }

    public PokemonSpecies() {}

}
