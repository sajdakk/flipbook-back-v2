package com.sajdakk.flipbook.controllers;

import com.sajdakk.flipbook.dtos.AddAvatarDto;
import com.sajdakk.flipbook.models.UsersModel;
import com.sajdakk.flipbook.utils.JwtUtil;
import com.sajdakk.flipbook.views.ProfileView;
import com.sajdakk.flipbook.views.UserView;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController("/users")
public class UsersController {

    private final UsersModel usersModel;
    private final JwtUtil jwtUtil;

    @Autowired
    public UsersController(UsersModel usersModel, JwtUtil jwtUtil) {
        this.usersModel = usersModel;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/users")
    public List<UserView> getAll(HttpServletRequest request) {
        Claims claims = jwtUtil.resolveClaims(request);
        if (claims == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be logged in to access this resource");
        }

        Object role = claims.get("role");
        if (role == null || !role.equals(3)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be admin to access this resource");
        }

        return UserView.fromEntities(usersModel.getAllUsers());
    }

    @GetMapping("/users/{id}")
    public UserView get(HttpServletRequest request, @PathVariable("id") int id) {
        Claims claims = jwtUtil.resolveClaims(request);
        if (claims == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be logged in to access this resource");
        }

        Object currentUserId = claims.get("user_id");
        if (currentUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be logged in to access this resource");
        }

        Object role = claims.get("role");
        if (!currentUserId.equals(id) && (role == null || !role.equals(3))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be admin to access this resource");
        }

        return UserView.fromEntity(usersModel.getUsersById(id));
    }

    @GetMapping("/users/{id}/profile")
    public ProfileView profile(HttpServletRequest request, @PathVariable("id") int id) {
        Claims claims = jwtUtil.resolveClaims(request);
        if (claims == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be logged in to access this resource");
        }


        Object currentUserId = claims.get("user_id");
        if (currentUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be logged in to access this resource");
        }

        Object role = claims.get("role");
        if (!currentUserId.equals(id) && (role == null || !role.equals(3))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be admin to access this resource");
        }

        return ProfileView.fromEntity(usersModel.getUsersById(id));
    }

    @DeleteMapping("/users/{id}")
    public void delete(HttpServletRequest request, @PathVariable("id") int id) {
        Claims claims = jwtUtil.resolveClaims(request);
        if (claims == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be logged in to access this resource");
        }

        Object role = claims.get("role");
        if (role == null || !role.equals(3)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be admin to access this resource");
        }

        usersModel.removeUser(id);
    }

    @RequestMapping(consumes = "multipart/form-data", method = {RequestMethod.POST}, path = {"/users/{id}/avatar"})
    public void updateAvatar(HttpServletRequest request, @PathVariable("id") int id, @RequestPart("file") MultipartFile file) {
        Claims claims = jwtUtil.resolveClaims(request);
        if (claims == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be logged in to access this resource");
        }

        Object loggedUserId = claims.get("user_id");
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
    public void deleteAvatar(HttpServletRequest request, @PathVariable("id") int id) {
        Claims claims = jwtUtil.resolveClaims(request);
        if (claims == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be logged in to access this resource");
        }

        Object loggedUserId = claims.get("user_id");
        if (loggedUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be log in to access this resource");
        }

        if (!loggedUserId.equals(id)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not allowed to access this resource");
        }

        usersModel.deleteUserAvatar(id);
    }

    @PostMapping("/users/{id}/toggle-admin")
    public void upgrade(HttpServletRequest request, @PathVariable("id") int id) {
        final Claims claims = jwtUtil.resolveClaims(request);
        if (claims == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be logged in to access this resource");
        }

        final Object role = claims.get("role");
        if (role == null || !role.equals(3)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be admin to access this resource");
        }

        usersModel.toggleAdmin(id);
    }
}
