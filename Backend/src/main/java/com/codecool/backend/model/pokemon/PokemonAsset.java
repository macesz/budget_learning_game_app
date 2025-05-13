package com.codecool.backend.model.pokemon;
import jakarta.persistence.*;
import lombok.*;

@Data

@NoArgsConstructor
@Entity
@Table(name = "pokemon_assets")
public class PokemonAsset {
    @Getter
    @Setter
    @Id
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "picture_url")
    private String pictureUrl;

    @Column(name = "gif_url")
    private String gifUrl;

    @Column(name = "base_happiness")
    private Integer baseHappiness;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "species_id")
    private PokemonSpecies species;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id")
    private EvolutionChain evolution;
}
