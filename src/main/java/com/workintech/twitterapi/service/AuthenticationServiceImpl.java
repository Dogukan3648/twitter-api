package com.workintech.twitterapi.service;

import com.workintech.twitterapi.dto.LoginDTO;
import com.workintech.twitterapi.exception.TwitterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
   public void login(LoginDTO loginDTO){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.email(),loginDTO.password())
            );
        }catch (AuthenticationException exception){
            throw new TwitterException("Invalid email or password.", HttpStatus.UNAUTHORIZED);
        }
    }
}
