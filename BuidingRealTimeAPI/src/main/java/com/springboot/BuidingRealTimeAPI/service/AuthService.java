package com.springboot.BuidingRealTimeAPI.service;

import com.springboot.BuidingRealTimeAPI.dto.LoginDto;
import com.springboot.BuidingRealTimeAPI.dto.RegisterDto;

public interface AuthService {

    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
