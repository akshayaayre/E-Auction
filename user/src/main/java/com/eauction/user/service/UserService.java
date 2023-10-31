package com.eauction.user.service;

import com.eauction.user.dto.LoginCredentials;
import com.eauction.user.entity.UserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public interface UserService {
    public UserDetails findByEmail(String email);
    public UserDetails registerUser(UserDetails request);
    public String generateToken(String username);
    public boolean validateToken(String token);
    public UserDetails getUserFromToken(String token);
    public String hashPassword(String password);
    public boolean matchesPassword(String rawPassword, String encodedPassword);
}
