package com.codecool.backend.model.entity;

import com.codecool.backend.controller.dto.UserEntityDto;
import com.codecool.backend.controller.dto.UserEntityRegistrationDto;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@SequenceGenerator(name="seq", initialValue=2, allocationSize=100)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq")
    private int id;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Column(nullable = false)
    private BigDecimal targetAmount;

    @OneToMany(mappedBy = "userEntity")
    private List<Transaction> transactions;

    // join category with member

    public UserEntity() {
    }

    public UserEntity(String name) {
        this.name = name;
    }

    public UserEntity(UserEntityRegistrationDto memberRegistrationDto) {
        name = memberRegistrationDto.name();
        email = memberRegistrationDto.email();
        password = memberRegistrationDto.password();
    }



    public UserEntity(UserEntityDto userDto) {
        id = userDto.id();
        name = userDto.name();
        email = userDto.email();
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity member = (UserEntity) o;
        return id == member.id
                && Objects.equals(name, member.name)
                && Objects.equals(email, member.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }

}



