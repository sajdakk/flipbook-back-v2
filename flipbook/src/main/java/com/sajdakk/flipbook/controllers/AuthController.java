package com.sajdakk.flipbook.controllers;

import com.sajdakk.flipbook.dtos.LoginDto;
import com.sajdakk.flipbook.dtos.RegisterDto;
import com.sajdakk.flipbook.entities.UserEntity;
import com.sajdakk.flipbook.models.UsersModel;
import com.sajdakk.flipbook.repositories.BooksRepository;
import com.sajdakk.flipbook.views.UserView;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/auth")
public class AuthController {
    UsersModel usersModel;

    @Autowired
    public AuthController(UsersModel usersModel) {
        this.usersModel = usersModel;
    }

    @PostMapping("login")
    public UserView login(HttpSession session, @RequestBody LoginDto dto) {
        UserEntity user = usersModel.verifyUser(dto.getEmail(), dto.getPassword());

        setSession(session, user);
        return UserView.fromEntity(user);

    }

    @PostMapping("register")
    public UserView register(HttpSession session, @RequestBody RegisterDto dto) {
        UserEntity user = usersModel.createUser(dto);

        setSession(session, user);
        return UserView.fromEntity(user);

    }

    @PostMapping("logout")
    public void logout(HttpSession session) {
        session.invalidate();
    }

    private void setSession(HttpSession session, UserEntity user) {
        session.setAttribute("user_id", user.getId());
        session.setAttribute("email", user.getEmail());
        session.setAttribute("name", user.getName());
        session.setAttribute("surname", user.getSurname());
        session.setAttribute("role", user.getRole().getId());
    }
}
