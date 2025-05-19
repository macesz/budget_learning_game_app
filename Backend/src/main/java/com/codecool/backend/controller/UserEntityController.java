package com.codecool.backend.controller;

import com.codecool.backend.controller.dto.*;
import com.codecool.backend.security.jwt.JwtUtils;
import com.codecool.backend.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/api/user")
public class UserEntityController {

    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserEntityService userEntityService;

    @Autowired
    public UserEntityController(UserEntityService memberService, PasswordEncoder encoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userEntityService = memberService;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserEntitySignInDto loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        User userDetails = (User) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .toList();
        com.codecool.backend.model.entity.UserEntity loggedMember = userEntityService.findMemberByEmail(userDetails.getUsername());
        return ResponseEntity
                .ok(new JwtResponse(jwt, loggedMember.getName(), roles));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> createUser(@RequestBody UserEntityRegistrationDto signUpRequest) {

            return userEntityService.register(signUpRequest, encoder);
    }

    @GetMapping("/profile")
//    @PreAuthorize("isAuthenticated()")
    public UserEntityProfileDto getProfile() {
        // Get current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();

        // Get the member associated with the email
        com.codecool.backend.model.entity.UserEntity currentMember = userEntityService.findMemberByEmail(currentUserEmail);

        return new UserEntityProfileDto(currentMember.getId(), currentMember.getName(), currentMember.getEmail(), currentMember.getTargetAmount());
    }

    @GetMapping("/{id}")
    public UserEntityDto getUser(@PathVariable int id) {
        return userEntityService.getMember(id);
    }

    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable int id) {
        return userEntityService.deleteMember(id);
    }


    @PutMapping("/profile/update")
    public ResponseEntity<?> updateUser(@RequestBody UpdateProfileDto profileDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();

        com.codecool.backend.model.entity.UserEntity currentMember = userEntityService.findMemberByEmail(currentUserEmail);

        if(profileDto.currentPassword() != null && !profileDto.currentPassword().isEmpty()
                && profileDto.newPassword() != null && !profileDto.newPassword().isEmpty()) {

            if (!encoder.matches(profileDto.currentPassword(), currentMember.getPassword())) {
                return ResponseEntity.badRequest().body("Wrong current password");
            }

            currentMember.setPassword(encoder.encode(profileDto.newPassword()));
        }

        if (profileDto.username() != null) {
            currentMember.setName(profileDto.username());
        }

        if (profileDto.newTargetAmount() != null) {
            currentMember.setTargetAmount(profileDto.newTargetAmount());
        }

        boolean updated = userEntityService.updateMember(currentMember);
        if (updated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("Failed to update user");
        }
    }

//    @GetMapping("/currentPokemon")
//    public MyPokemonDto getMyPokemon(){
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        return memberService.getMyPokemon(user.getUsername());
//    }

    @GetMapping("/savings")
    public BigDecimal getMySavings(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userEntityService.getMySaving(user.getUsername());
    }
}
