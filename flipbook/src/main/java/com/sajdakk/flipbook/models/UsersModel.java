package com.sajdakk.flipbook.models;

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

//    public void createUser(String username, String password) {
//        UserEntity.builder().name(username).password(password).build();
//        usersRepository.save(new UserEntity(username, password));
//    }
}
