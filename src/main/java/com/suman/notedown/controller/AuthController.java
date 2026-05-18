package com.suman.notedown.controller;

import com.suman.notedown.dto.AuthRequestDTO;
import com.suman.notedown.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequestDTO authRequestDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
            return ResponseEntity.status(HttpStatus.OK).body(jwtService.generateToken(authRequestDTO.getUsername()));
        } catch (AuthenticationException e) {
            System.out.println("!! AuthenticationException thrown !!");
            throw new RuntimeException(e);
        }
    }
}
