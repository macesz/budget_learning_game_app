package com.codecool.backend.service;

import com.codecool.backend.controller.dto.UpdateProfileDto;
import com.codecool.backend.controller.dto.UserEntityDto;
import com.codecool.backend.controller.dto.UserEntityRegistrationDto;
import com.codecool.backend.controller.exception.HouseholdNotFoundException;
import com.codecool.backend.controller.exception.UserEntityNotFoundException;
import com.codecool.backend.model.entity.Household;
import com.codecool.backend.model.entity.UserEntity;
import com.codecool.backend.model.entity.Role;
import com.codecool.backend.model.entity.Transaction;
import com.codecool.backend.repository.UserEntityRepository;
import com.codecool.backend.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
public class UserEntityService {

    private final UserEntityRepository userEntityRepository;
    private final TransactionRepository transactionRepository;
    private final HouseHoldService householdService;

    @Autowired
    public UserEntityService(UserEntityRepository userEntityRepository, TransactionRepository transactionRepository, HouseHoldService householdService) {
        this.userEntityRepository = userEntityRepository;
        this.transactionRepository = transactionRepository;
        this.householdService = householdService;
    }


    public UserEntityDto register(UserEntityRegistrationDto signUpRequest, PasswordEncoder encoder) {
        Household household = householdService.createHousehold();

        UserEntity userEntity = new UserEntity();
        userEntity.setName(signUpRequest.name());
        userEntity.setPassword(encoder.encode(signUpRequest.password()));
        userEntity.setEmail(signUpRequest.email());
        userEntity.setRoles(Set.of(Role.ROLE_USER));
        userEntity.setTargetAmount(new BigDecimal(0));
        userEntity.setHousehold(household);

        UserEntity savedEntity = userEntityRepository.save(userEntity);

        // Use the constructor that takes a UserEntity
        return new UserEntityDto(savedEntity);
    }

    public UserEntityDto updateProfile(String currentUserEmail, UpdateProfileDto updateProfileDto, PasswordEncoder encoder) {
        UserEntity currentUser = userEntityRepository.findUserByEmail(currentUserEmail)
                .orElseThrow(UserEntityNotFoundException::new);

        if (!encoder.matches(updateProfileDto.currentPassword(), currentUser.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        if (updateProfileDto.username() != null && !updateProfileDto.username().isEmpty()) {
            currentUser.setName(updateProfileDto.username());
        }

        if (updateProfileDto.email() != null && !updateProfileDto.email().isEmpty()) {
            currentUser.setEmail(updateProfileDto.email());
        }

        if (updateProfileDto.newPassword() != null && !updateProfileDto.newPassword().isEmpty()) {
            currentUser.setPassword(encoder.encode(updateProfileDto.newPassword()));
        }

        if (updateProfileDto.newTargetAmount() != null) {
            currentUser.setTargetAmount(updateProfileDto.newTargetAmount());
        }

        if (updateProfileDto.connectToUserEmail() != null && !updateProfileDto.connectToUserEmail().isEmpty()) {
            UserEntity targetUser = userEntityRepository.findUserByEmail(updateProfileDto.connectToUserEmail())
                    .orElseThrow(UserEntityNotFoundException::new);

            Household targetHousehold = targetUser.getHousehold();
            currentUser.setHousehold(targetHousehold);
        }

        UserEntity updatedUser = userEntityRepository.save(currentUser);
        return new UserEntityDto(updatedUser);
    }

    public UserEntityDto getUserEntity(Long id) {
        UserEntity member = userEntityRepository.getUserById(id)
                .orElseThrow(UserEntityNotFoundException::new);
        return new UserEntityDto(member);
    }

    public boolean deleteUserEntity(Long id) {
        return userEntityRepository.deleteUserEntityById(id);
    }


    public UserEntity findUserByEmail(String email){
        return userEntityRepository.findUserByEmail(email).orElse(null);
    }

    public BigDecimal getMySaving(String email) {
        UserEntity member = userEntityRepository.findUserByEmail(email)
                .orElseThrow(UserEntityNotFoundException::new);
        List<Transaction> transactions = transactionRepository.getAllByUserEntity(member).orElse(null);
        assert transactions != null;

        BigDecimal totalTransactions = transactions
                .stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return member.getTargetAmount().subtract(totalTransactions);
    }

    public List<UserEntityDto> getHouseholdMembers(Long householdId) {
        Household household = householdService.findHouseholdById(householdId)
                .orElseThrow(() -> new HouseholdNotFoundException("Household not found"));

        List<UserEntity> householdMembers = userEntityRepository.findAllByHousehold(household);

        return householdMembers.stream()
                .map(UserEntityDto::new)
                .toList();
    }

    public List<UserEntityDto> getCurrentUserHouseholdMembers(String userEmail) {
        UserEntity currentUser = userEntityRepository.findUserByEmail(userEmail)
                .orElseThrow(UserEntityNotFoundException::new);

        Household household = currentUser.getHousehold();
        if (household == null) {
            throw new IllegalStateException("User does not belong to any household");
        }

        List<UserEntity> householdMembers = userEntityRepository.findAllByHousehold(household);

        return householdMembers.stream()
                .map(UserEntityDto::new)
                .toList();
    }

    public boolean isHouseholdEmpty(Long householdId) {
        return !userEntityRepository.existsByHouseholdId(householdId);
    }

}
