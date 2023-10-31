package com.eauction.user.controller;

import com.eauction.user.dto.LoginCredentials;
import com.eauction.user.entity.UserDetails;
import com.eauction.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping(value = "/e-auction/api/v1/user")
@RestController
//@CrossOrigin("*")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/registerUser")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDetails request){
        try {
            if(userService.findByEmail(request.getEmail()) != null){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
            }

            String hashedPassword = userService.hashPassword(request.getPassword());
            request.setPassword(hashedPassword);

            UserDetails userDetails = userService.registerUser(request);
            return ResponseEntity.ok("User registered successfully");
        }
        catch (Exception e){
            return new ResponseEntity(e.getStackTrace(),HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginCredentials request){
//        return userService.authenticateUser(request);
        UserDetails existingUser = userService.findByEmail(request.getEmail());

        if(existingUser == null || !userService.matchesPassword(request.getPassword(),existingUser.getPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
        }

        String token = userService.generateToken(existingUser.getEmail());
        existingUser.setToken(token);
        existingUser.setPassword(null);
        return ResponseEntity.ok(existingUser);
    }

    @GetMapping("/authenticate-user")
    public ResponseEntity<UserDetails> authenticateUser(@RequestParam String token){
        if(userService.validateToken(token)){
            UserDetails existingUser = userService.getUserFromToken(token);
            return ResponseEntity.ok(existingUser);
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}
