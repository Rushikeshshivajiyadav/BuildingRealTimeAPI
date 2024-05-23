package com.springboot.BuidingRealTimeAPI.service.impl;

import com.springboot.BuidingRealTimeAPI.configure.SecurityConfig;
import com.springboot.BuidingRealTimeAPI.dto.LoginDto;
import com.springboot.BuidingRealTimeAPI.dto.RegisterDto;
import com.springboot.BuidingRealTimeAPI.entity.Role;
import com.springboot.BuidingRealTimeAPI.entity.User;
import com.springboot.BuidingRealTimeAPI.exception.AppAPIException;
import com.springboot.BuidingRealTimeAPI.repository.RoleRepository;
import com.springboot.BuidingRealTimeAPI.repository.UserRepository;
import com.springboot.BuidingRealTimeAPI.security.JwtTokenProvider;
import com.springboot.BuidingRealTimeAPI.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    private JwtTokenProvider jwtTokenProvider;


    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),
                                                                                    loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        return token;
    }

    @Override
    public String register(RegisterDto registerDto) {

        // add check for username exists in database
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new AppAPIException(HttpStatus.BAD_REQUEST, "Username Already exists ... !");
        }

        // add check for email exists in database
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new AppAPIException(HttpStatus.BAD_REQUEST, "Email Already exists ... !");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_ADMIN").get();
        roles.add(userRole);
        user.setRole(roles);

        userRepository.save(user);

        return "User Register successfully";
    }
}
