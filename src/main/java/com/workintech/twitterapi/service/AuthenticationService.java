package com.workintech.twitterapi.service;

import com.workintech.twitterapi.dto.LoginDTO;

public interface AuthenticationService {
    void login(LoginDTO loginDTO);
}
