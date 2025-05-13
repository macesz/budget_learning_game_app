package com.codecool.backend.model.pokemon;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Entity
@Table(name = "evolution_chain")
public class EvolutionChain {

    @Id
    private Integer id;

    @OneToMany(mappedBy = "evolutionChain", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PokemonSpecies> evolutions = new ArrayList<>();

    public EvolutionChain() {}

    public EvolutionChain(Integer id, List<PokemonSpecies> evolutions) {
        this.id = id;
        this.evolutions = evolutions;
    }
}