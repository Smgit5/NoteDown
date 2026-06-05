package com.suman.notedown.controller;

import com.suman.notedown.dto.userDtos.AuthRequestDTO;
import com.suman.notedown.dto.userDtos.UserRegisterRequestDTO;
import com.suman.notedown.dto.userDtos.UserResponseDTO;
import com.suman.notedown.entity.User;
import com.suman.notedown.service.JwtService;
import com.suman.notedown.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRegisterRequestDTO userRegReqDTO) {
        System.out.println("Inside register controller...");
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(userRegReqDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequestDTO authRequestDTO) {
        Authentication authResponse = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        User user = (User) authResponse.getPrincipal();
        String username = user.getUsername();
        String role = user.getRole().name();
        return ResponseEntity.status(HttpStatus.OK).body(jwtService.generateToken(username, role));
    }
}
