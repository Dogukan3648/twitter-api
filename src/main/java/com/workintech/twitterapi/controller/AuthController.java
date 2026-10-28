package com.workintech.twitterapi.controller;

import com.workintech.twitterapi.dto.LoginDTO;
import com.workintech.twitterapi.entity.User;
import com.workintech.twitterapi.service.AuthenticationService;
import com.workintech.twitterapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private UserService userService;
    private AuthenticationService authenticationService;

    @Autowired
    public AuthController(UserService userService, AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public void register(@Valid @RequestBody User user) {
        userService.register(user);
    }

    @PostMapping("/login")
    public void login(@Valid @RequestBody LoginDTO loginDTO) {
        authenticationService.login(loginDTO);
    }
}
