package com.codecool.backend.model.pokemon;
import com.codecool.backend.model.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user_pokemon")
public class UserPokemon {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    @Column(nullable = false)
    private Integer happiness = 70;

    @Column(name = "picture_url", nullable = false)
    private String pictureUrl;

    @Column(name = "gif_url",nullable = false)
    private String gifUrl;

    @Column(name = "hatch_date", nullable = false)
    private LocalDateTime hatchDate = LocalDateTime.now();

    @Column(name = "is_evolution_pending")
    private Boolean isEvolutionPending = false;

    @ManyToOne
    @JoinColumn(name = "species_id", referencedColumnName = "id", insertable = false, updatable = false)
    private PokemonSpecies species;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private UserEntity user;

    @Column(name = "experience", nullable = false)
    private Integer experience;
}
