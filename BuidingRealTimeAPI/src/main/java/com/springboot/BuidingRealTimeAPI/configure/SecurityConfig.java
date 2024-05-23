package com.springboot.BuidingRealTimeAPI.configure;

import com.springboot.BuidingRealTimeAPI.security.JwtAuthenticationFilter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@SecurityScheme(
        name = "Bear Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SecurityConfig {

    private UserDetailsService userDetailsService;

    private AuthenticationEntryPoint authenticationEntryPoint;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(UserDetailsService userDetailsService, AuthenticationEntryPoint authenticationEntryPoint,
                          JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public static AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }
    @Bean
    public static PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf().disable().authorizeHttpRequests((authorize) ->
//            authorize.anyRequest().authenticated()
                authorize.antMatchers(HttpMethod.GET,"/api/**").permitAll()
                        .antMatchers(HttpMethod.POST,"/api/v1/auth/**").permitAll()
                        .antMatchers(HttpMethod.PUT,"/api/v1/**").permitAll()
                        .antMatchers(HttpMethod.DELETE,"/api/v1/**").permitAll()
                        .antMatchers(HttpMethod.GET,"/swagger-ui/**").permitAll()
                        .antMatchers(HttpMethod.GET,"/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated()
        ).exceptionHandling((exception) -> exception.authenticationEntryPoint(authenticationEntryPoint))
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService(){
//
//        UserDetails Rushi = User.builder()
//                .username("Rushi")
//                .password(passwordEncoder().encode("Rushi"))
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("Abhi")
//                .password(passwordEncoder().encode("Abhi"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(Rushi, admin);
//    }
}
