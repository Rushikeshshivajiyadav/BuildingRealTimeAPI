package com.springboot.BuidingRealTimeAPI.controller;

import com.springboot.BuidingRealTimeAPI.dto.JWTAuthResponse;
import com.springboot.BuidingRealTimeAPI.dto.LoginDto;
import com.springboot.BuidingRealTimeAPI.dto.RegisterDto;
import com.springboot.BuidingRealTimeAPI.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Build Login Rest API
    @PostMapping(value = {"/login","/signin"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){

     String token = authService.login(loginDto);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);
     return  ResponseEntity.ok(jwtAuthResponse);
    }

    // Build Register Rest API
    @PostMapping(value = {"/register","/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){

        String response = authService.register(registerDto);
        return  new ResponseEntity(response, HttpStatus.CREATED);
    }
}
