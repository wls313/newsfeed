package com.fiveman.newsfeed.auth.controller;

import com.fiveman.newsfeed.auth.dto.LoginRequestDto;
import com.fiveman.newsfeed.auth.dto.LoginResponseDto;
import com.fiveman.newsfeed.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequest) {

        LoginResponseDto login = authService.login(loginRequest);

        return ResponseEntity.ok(login);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        authService.logout();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}