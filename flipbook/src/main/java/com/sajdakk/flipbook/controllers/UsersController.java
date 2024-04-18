package com.sajdakk.flipbook.controllers;

import com.sajdakk.flipbook.dtos.AddAvatarDto;
import com.sajdakk.flipbook.models.UsersModel;
import com.sajdakk.flipbook.views.ProfileView;
import com.sajdakk.flipbook.views.UserView;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.Console;
import java.util.List;

@RestController("/users")
public class UsersController {

    UsersModel usersModel;

    @Autowired
    public UsersController(UsersModel usersModel) {
        this.usersModel = usersModel;
    }

    @GetMapping("/users")
    public List<UserView> getAll(HttpSession session) {
        Object role = session.getAttribute("role");
        if (role == null || !role.equals(3)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be admin to access this resource");
        }

        return UserView.fromEntities(usersModel.getAllUsers());
    }

    @GetMapping("/users/{id}")
    public UserView get(HttpSession session, @PathVariable("id") int id) {
        Object currentUserId = session.getAttribute("user_id");
        if (currentUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be logged in to access this resource");
        }

        Object role = session.getAttribute("role");
        if (!currentUserId.equals(id) && (role == null || !role.equals(3))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be admin to access this resource");
        }

        return UserView.fromEntity(usersModel.getUsersById(id));
    }

    @GetMapping("/users/{id}/profile")
    public ProfileView profile(HttpSession session, @PathVariable("id") int id) {
        Object currentUserId = session.getAttribute("user_id");
        if (currentUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be logged in to access this resource");
        }

        Object role = session.getAttribute("role");
        if (!currentUserId.equals(id) && (role == null || !role.equals(3))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be admin to access this resource");
        }

        return ProfileView.fromEntity(usersModel.getUsersById(id));
    }

    @DeleteMapping("/users/{id}")
    public void delete(HttpSession session, @PathVariable("id") int id) {
        Object role = session.getAttribute("role");
        if (role == null || !role.equals(3)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be admin to access this resource");
        }

        usersModel.removeUser(id);
    }

    @RequestMapping(consumes = "multipart/form-data", method = {RequestMethod.POST}, path = {"/users/{id}/avatar"})
    public void updateAvatar(HttpSession session, @PathVariable("id") int id, @RequestPart("file") MultipartFile file) {
        Object loggedUserId = session.getAttribute("user_id");
        if (loggedUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be log in to access this resource");
        }

        if (!loggedUserId.equals(id)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not allowed to access this resource");
        }

        String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

        byte[] imageData;

        try {
            imageData = file.getBytes();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error reading file");
        }

        AddAvatarDto dto = new AddAvatarDto(
                imageData,
                extension
        );
        usersModel.updateUserAvatar(id, dto);
    }

    @DeleteMapping("/users/{id}/avatar")
    public void deleteAvatar(HttpSession session, @PathVariable("id") int id) {
        Object loggedUserId = session.getAttribute("user_id");
        if (loggedUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be log in to access this resource");
        }

        if (!loggedUserId.equals(id)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not allowed to access this resource");
        }

        usersModel.deleteUserAvatar(id);
    }

    @PostMapping("/users/{id}/toggle-admin")
    public void upgrade(HttpSession session, @PathVariable("id") int id) {
        Object role = session.getAttribute("role");
        if (role == null || !role.equals(3)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be admin to access this resource");
        }

        usersModel.toggleAdmin(id);
    }


}
