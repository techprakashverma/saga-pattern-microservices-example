
package com.auth.ms.service;

import com.auth.ms.dto.LoginRequest;
import com.auth.ms.dto.RegisterRequest;
import com.auth.ms.entity.User;
import com.auth.ms.repository.UserRepository;
import com.auth.ms.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder encoder;

    public void register(RegisterRequest req) {

        User user = new User();

        user.setUsername(req.getUsername());
        user.setPassword(encoder.encode(req.getPassword()));
        user.setRole("ROLE_USER");

        repo.save(user);
    }

    public Object login(LoginRequest req) {

        User user = repo.findByUsername(req.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("Invalid username or password"));

        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Incorrect password!");
        }
        String token = jwtService.generateToken(user.getUsername(), user.getRole());

      /*return jwtService.generateToken(
        user.getUsername(),
        user.getRole());*/
        return Map.of(
                "token", token,
                "username", user.getUsername(),
                "role", user.getRole()
        );
    }

}
