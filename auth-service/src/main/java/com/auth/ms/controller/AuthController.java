
package com.auth.ms.controller;

import com.auth.ms.dto.LoginRequest;
import com.auth.ms.dto.RegisterRequest;
import com.auth.ms.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest req) {

        service.register(req);

        return "User registered";
    }

    /*@PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest req){
     String token = service.login(req);
     return new AuthResponse(token);
    }*/

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.login(request));
    }

}
