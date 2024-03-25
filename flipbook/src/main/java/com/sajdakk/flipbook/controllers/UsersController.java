package com.sajdakk.flipbook.controllers;

import com.sajdakk.flipbook.dtos.AddAvatarDto;
import com.sajdakk.flipbook.models.UsersModel;
import com.sajdakk.flipbook.views.UserView;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @DeleteMapping("/users/{id}")
    public void delete(HttpSession session, @PathVariable("id") int id) {
        Object role = session.getAttribute("role");
        if (role == null || !role.equals(3)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be admin to access this resource");
        }

        usersModel.removeUser(id);
    }


    @PostMapping("/users/{id}/avatar")
    public void updateAvatar(HttpSession session, @PathVariable("id") int id, @RequestBody AddAvatarDto addAvatarDto) {
        Object role = session.getAttribute("role");
        if (role == null || !role.equals(3)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be admin to access this resource");
        }

        usersModel.updateUserAvatar(id, addAvatarDto);
    }

    @GetMapping("/users/{id}/upgrade")
    public void upgrade(HttpSession session, @PathVariable("id") int id) {
        Object role = session.getAttribute("role");
        if (role == null || !role.equals(3)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be admin to access this resource");
        }

        usersModel.upgradeUser(id);
    }

    @GetMapping("/users/{id}/downgrade")
    public void downgrade(HttpSession session, @PathVariable("id") int id) {
        Object role = session.getAttribute("role");
        if (role == null || !role.equals(3)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to be admin to access this resource");
        }

        usersModel.downgradeUser(id);
    }

}
