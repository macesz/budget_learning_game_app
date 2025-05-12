package com.codecool.backend.service;

import com.codecool.backend.controller.dto.UserEntityDto;
import com.codecool.backend.controller.dto.UserEntityRegistrationDto;
import com.codecool.backend.controller.exception.UserEntityNotFoundException;
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

    @Autowired
    public UserEntityService(UserEntityRepository userEntityRepository, TransactionRepository transactionRepository) {
        this.userEntityRepository = userEntityRepository;
        this.transactionRepository = transactionRepository;
    }


    public ResponseEntity<Void> register(UserEntityRegistrationDto signUpRequest, PasswordEncoder encoder) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(signUpRequest.name());
        userEntity.setPassword(encoder.encode(signUpRequest.password()));
        userEntity.setEmail(signUpRequest.email());
        userEntity.setRoles(Set.of(Role.ROLE_USER));
        userEntity.setTargetAmount(new BigDecimal(0));
        userEntityRepository.save(userEntity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public UserEntityDto getMember(int id) {
        UserEntity member = userEntityRepository.getUserEntityById(id)
                .orElseThrow(UserEntityNotFoundException::new);
        return new UserEntityDto(member);
    }

    public boolean deleteMember(int id) {
        return userEntityRepository.deleteUserEntityById(id);
    }

    public boolean updateMember(UserEntity member) {
        userEntityRepository.save(member);
        return true;
    }

    public UserEntity findMemberByEmail(String email){
        return userEntityRepository.findUserEntityByEmail(email).orElse(null);
    }

    public int getMySaving(String email) {
        UserEntity member = userEntityRepository.findUserEntityByEmail(email)
                .orElseThrow(UserEntityNotFoundException::new);
        List<Transaction> transactions = transactionRepository.getAllByUserEntity(member).orElse(null);
        assert transactions != null;
        return member.getTargetAmount().intValue()-(transactions
                .stream()
                .mapToInt(Transaction::getAmount).sum());
    }
}
