package com.sajdakk.flipbook.models;

import com.sajdakk.flipbook.dtos.RegisterDto;
import com.sajdakk.flipbook.entities.ReviewEntity;
import com.sajdakk.flipbook.entities.RoleEntity;
import com.sajdakk.flipbook.entities.UserEntity;
import com.sajdakk.flipbook.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
public class UsersModel {
    private final String defaultPassword = "$2y$10$wkR1VwX2yoFkmUQibWGoqe/kuUmIuu09jE8EHkC1gc1hTTOqez1sG";

    UsersRepository usersRepository;
    PasswordEncoder passwordEncoder;

    @Autowired
    public UsersModel(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public UserEntity verifyUser(String email, String password) {
        UserEntity user = usersRepository.findByEmail(email);
        String userPassword;
        if (user == null) {
            // Even if the user is not found, we still want to
            // check the password to prevent timing attacks.
            userPassword = defaultPassword;
        } else {
            userPassword = user.getPassword();
        }

        boolean isCorrect = passwordEncoder.matches(password, userPassword);
        if (!isCorrect || user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }

        return user;
    }

    public UserEntity createUser(RegisterDto dto) {
        UserEntity user = UserEntity.builder()
                .name(dto.getName())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .surname(dto.getSurname())
                .role(RoleEntity.builder().id(2).build())
                .build();
        return usersRepository.save(user);
    }

    public List<UserEntity> getAllUsers() {
        return usersRepository.findAll();
    }

    public void removeUser(Integer id) {
        usersRepository.removeUserById(id);
    }

    public void updateUserAvatar(Integer id, String avatar) {
        UserEntity user = usersRepository.findById(id).orElse(null);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");
        }

        user.setAvatar(avatar);
        usersRepository.save(user);
    }

    public void upgradeUser(Integer id) {
        UserEntity user = usersRepository.findById(id).orElse(null);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");
        }

        user.setRole(RoleEntity.builder().id(3).build());
        usersRepository.save(user);
    }

    public void downgradeUser(Integer id) {
        UserEntity user = usersRepository.findById(id).orElse(null);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");
        }

        user.setRole(RoleEntity.builder().id(2).build());
        usersRepository.save(user);
    }
}
