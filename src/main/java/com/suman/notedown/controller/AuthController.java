package com.suman.notedown.controller;

import com.suman.notedown.dto.userDtos.AuthRequestDTO;
import com.suman.notedown.dto.userDtos.UserRegisterRequestDTO;
import com.suman.notedown.dto.userDtos.UserResponseDTO;
import com.suman.notedown.service.JwtService;
import com.suman.notedown.service.UserService;
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


    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRegisterRequestDTO userRegReqDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(userRegReqDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequestDTO authRequestDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
            return ResponseEntity.status(HttpStatus.OK).body(jwtService.generateToken(authRequestDTO.getUsername()));
        } catch (AuthenticationException e) {
            System.out.println("Inside AuthController :: login, msg = " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
