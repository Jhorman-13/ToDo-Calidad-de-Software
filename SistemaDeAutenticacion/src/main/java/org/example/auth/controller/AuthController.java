package org.example.auth.controller;

import org.example.auth.dto.LoginRequest;
import org.example.auth.dto.RegisterRequest;
import org.example.auth.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")

public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {

        this.service = service;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return service.register(request.username(), request.password());
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return service.login(request.username(), request.password());
    }

    @PostMapping("/recover")
    public String recover(@RequestParam String username) {
        return service.recover(username);
    }
}
