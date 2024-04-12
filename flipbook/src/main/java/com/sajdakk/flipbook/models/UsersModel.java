package com.sajdakk.flipbook.models;

import com.sajdakk.flipbook.dtos.AddAvatarDto;
import com.sajdakk.flipbook.dtos.RegisterDto;
import com.sajdakk.flipbook.entities.RoleEntity;
import com.sajdakk.flipbook.entities.UserEntity;
import com.sajdakk.flipbook.repositories.UsersRepository;
import com.sajdakk.flipbook.services.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Base64;
import java.util.List;

@Component
public class UsersModel {

    UsersRepository usersRepository;
    UploadService uploadService;
    PasswordEncoder passwordEncoder;

    @Autowired
    public UsersModel(UsersRepository usersRepository, UploadService uploadService) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.uploadService = uploadService;
    }

    public UserEntity verifyUser(String email, String password) {
        UserEntity user = usersRepository.findByEmail(email);
        String userPassword;
        if (user == null) {
            // Even if the user is not found, we still want to
            // check the password to prevent timing attacks.
            userPassword = "$2y$10$wkR1VwX2yoFkmUQibWGoqe/kuUmIuu09jE8EHkC1gc1hTTOqez1sG";
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
        UserEntity existUser = usersRepository.findByEmail(dto.getEmail());
        if (existUser != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with this email already exists");
        }

        UserEntity user = UserEntity.builder()
                .name(dto.getName())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .surname(dto.getSurname())
                .role(RoleEntity.builder().id(2).build())
                .build();
        return usersRepository.save(user);
    }

    public UserEntity getUsersById(Integer id) {
        return usersRepository.findById(id).orElse(null);
    }

    public List<UserEntity> getAllUsers() {
        return usersRepository.findAll();
    }


    public void removeUser(Integer id) {
        usersRepository.removeUserById(id);
    }

    public void updateUserAvatar(Integer id, AddAvatarDto addAvatarDto) {
        UserEntity user = usersRepository.findById(id).orElse(null);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");
        }

        String imagePath = uploadService.uploadFile(addAvatarDto.getBytes(), addAvatarDto.getImageExtension());
        if (imagePath == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error uploading image");
        }

        final String oldAvatar = user.getAvatar();
        if (oldAvatar != null) {
            uploadService.deleteFile(oldAvatar);
        }
        
        user.setAvatar(imagePath);
        usersRepository.save(user);
    }

    public void deleteUserAvatar(int id) {
        UserEntity user = usersRepository.findById(id).orElse(null);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");
        }

        uploadService.deleteFile(user.getAvatar());
        user.setAvatar(null);
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
