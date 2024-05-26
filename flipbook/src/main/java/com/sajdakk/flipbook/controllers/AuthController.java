package com.sajdakk.flipbook.controllers;

import com.sajdakk.flipbook.dtos.LoginDto;
import com.sajdakk.flipbook.dtos.RegisterDto;
import com.sajdakk.flipbook.entities.UserEntity;
import com.sajdakk.flipbook.models.UsersModel;
import com.sajdakk.flipbook.utils.JwtUtil;
import com.sajdakk.flipbook.views.UserView;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/auth")
public class AuthController {
    private final UsersModel usersModel;
    private final JwtUtil jwtUtil;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public AuthController(UsersModel usersModel, JwtUtil jwtUtil, RabbitTemplate rabbitTemplate) {
        this.usersModel = usersModel;
        this.jwtUtil = jwtUtil;
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("login")
    public UserView login(HttpServletResponse response, @RequestBody LoginDto dto) {
        UserEntity user = usersModel.verifyUser(dto.getEmail(), dto.getPassword());

        final Cookie cookie = new Cookie("X-Auth-Token", jwtUtil.createToken(user));
        cookie.setPath("/");

        response.addCookie(cookie);

        return UserView.fromEntity(user);
    }

    @PostMapping("register")
    public UserView register(HttpServletResponse response, @RequestBody RegisterDto dto) {
        UserEntity user = usersModel.createUser(dto);

        final Cookie cookie = new Cookie("X-Auth-Token", jwtUtil.createToken(user));
        cookie.setPath("/");

        response.addCookie(cookie);
        rabbitTemplate.convertAndSend("x.post-registration","", dto);

        return UserView.fromEntity(user);
    }

    @PostMapping("logout")
    public void logout(HttpServletResponse response) {
        final Cookie cookie = new Cookie("X-Auth-Token", "");
        cookie.setPath("/");

        response.addCookie(cookie);
    }
}
