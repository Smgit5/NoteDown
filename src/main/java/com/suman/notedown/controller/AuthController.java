package com.suman.notedown.controller;

import com.suman.notedown.dto.errorDtos.ErrorResponseDTO;
import com.suman.notedown.dto.userDtos.AuthRequestDTO;
import com.suman.notedown.dto.userDtos.UserRegisterRequestDTO;
import com.suman.notedown.dto.userDtos.UserResponseDTO;
import com.suman.notedown.entity.User;
import com.suman.notedown.service.JwtService;
import com.suman.notedown.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Register a new user")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "201", description = "User registered successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )),
                    @ApiResponse(responseCode = "409", description = "Username already exists", content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    ))
            }
    )
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRegisterRequestDTO userRegReqDTO) {
        System.out.println("Inside register controller...");
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(userRegReqDTO));
    }

    @Operation(summary = "Login user")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequestDTO authRequestDTO) {
        Authentication authResponse = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        User user = (User) authResponse.getPrincipal();
        String username = user.getUsername();
        String role = user.getRole().name();
        return ResponseEntity.status(HttpStatus.OK).body(jwtService.generateToken(username, role));
    }
}
