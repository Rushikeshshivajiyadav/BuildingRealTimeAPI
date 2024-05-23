package com.springboot.BuidingRealTimeAPI.security;

import com.springboot.BuidingRealTimeAPI.exception.AppAPIException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app-jwt-expiration-milliseconds}")
    private long jwtExprirationDate;

    // generate JWT Token

    public String generateToken(Authentication authentication){

        String username = authentication.getName();

        Date currentDate = new Date();

        Date expireDate = new Date(currentDate.getTime() + jwtExprirationDate);

        String token = Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(expireDate)
                .signWith(key())
                .compact();

        return token;

    }

    private SecretKey key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // get username from JWT Token
    public String getUsername(String token){

        return Jwts.parser().verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // validate JWT Token
    public  boolean validateToken(String token){

        try{
            Jwts.parser()
                    .verifyWith(key())
                    .build()
                    .parse(token);

            return true;
        }catch (MalformedJwtException malformedJwtException){
            throw  new AppAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT Token");
        }
        catch (ExpiredJwtException expiredJwtException){
            throw new AppAPIException(HttpStatus.BAD_REQUEST, "Expired JWT Token");
        }catch (UnsupportedJwtException unsupportedJwtException){
            throw  new AppAPIException(HttpStatus.BAD_REQUEST, "UnSupported JWT Token");
        }catch (IllegalArgumentException illegalArgumentException){
            throw new AppAPIException(HttpStatus.BAD_REQUEST, "Jwt Claims string is null or empty");
        }

    }
}
