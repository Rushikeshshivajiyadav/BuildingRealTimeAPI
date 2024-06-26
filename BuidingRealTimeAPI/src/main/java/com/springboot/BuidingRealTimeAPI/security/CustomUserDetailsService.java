package com.springboot.BuidingRealTimeAPI.security;

import com.springboot.BuidingRealTimeAPI.entity.Role;
import com.springboot.BuidingRealTimeAPI.entity.User;
import com.springboot.BuidingRealTimeAPI.exception.ResourceNotFoundException;
import com.springboot.BuidingRealTimeAPI.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameorEmail) throws UsernameNotFoundException {

        User user = userRepository.findByUsernameOrEmail(usernameorEmail,usernameorEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with Username and Email :"+ usernameorEmail));

//        Set<GrantedAuthority> authorities = user.getRole()
//                .stream().map( (role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());

          return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),mapRolesToAuthorities(user.getRole()));

    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){

        return roles.stream().map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
