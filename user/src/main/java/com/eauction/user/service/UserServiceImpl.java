package com.eauction.user.service;

import com.eauction.dto.UserEvent;
import com.eauction.user.dto.LoginCredentials;
import com.eauction.user.entity.UserDetails;
import com.eauction.user.repository.UserDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.*;

import javax.transaction.Transactional;
import java.security.Key;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService{

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private  Long jwtExpiration;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserDetailsRepo userDetailsRepo;

    @Autowired
    private KafkaTemplate<String, UserEvent> kafkaTemplate;

    private static final String USER_EVENT_TOPIC = "UserEventTopic";

    public UserDetails findByEmail(String email){
        if(email != null ){
            return userDetailsRepo.findByEmail(email);
        }
        return null;
    }

    @Transactional
    public UserDetails registerUser(UserDetails request){

        userDetailsRepo.save(request);

        //do not send password
        request.setPassword(null);

        //publish event to Kafka
        UserEvent userEvent = new UserEvent("USER_ADDED_UPDATED", request);
        kafkaTemplate.send(USER_EVENT_TOPIC, userEvent);

        return request;
    }

    public String generateToken(String username){
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder().setSubject(username).setIssuedAt(now).setExpiration(expiryDate).
                signWith(key, SignatureAlgorithm.HS512).compact();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(jwtSecret.getBytes()).parseClaimsJws(token);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    
    public UserDetails getUserFromToken(String token){
        Claims claims = Jwts.parser().setSigningKey(jwtSecret.getBytes()).parseClaimsJws(token).getBody();
        
        String username = claims.getSubject();
        UserDetails user = userDetailsRepo.findByEmail(username);
        user.setPassword(null);
        return user;
    }
    
    public String hashPassword(String password){
        return passwordEncoder.encode(password);
    }

    public boolean matchesPassword(String rawPassword, String encodedPassword){
        return passwordEncoder.matches(rawPassword,encodedPassword);
    }
//    public UserDetails authenticateUser(UserDetails request){
//        UserDetails user = null;
//        if(request != null && request.getEmail() != null && request.getPassword() != null){
//            user = userDetailsRepo.getUser(request.getEmail(),request.getPassword());
//        }
//        if(user != null)
//            return user;
//        else
//            return null;
//    }

}
