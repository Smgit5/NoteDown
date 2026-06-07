package com.suman.notedown.controller;

import com.suman.notedown.dto.userDtos.UserResponseDTO;
import com.suman.notedown.dto.userDtos.UserUpdateRequestDTO;
import com.suman.notedown.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/me")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // View profile
    @GetMapping
    public ResponseEntity<UserResponseDTO> viewProfile() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.viewProfile());
    }

    // Update profile
    @PatchMapping
    public ResponseEntity<UserResponseDTO> updateProfile(@Valid @RequestBody UserUpdateRequestDTO userUpdateRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateProfile(userUpdateRequestDTO));
    }

    // Delete profile
    @DeleteMapping
    public ResponseEntity<String>  deleteProfile() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteProfile());
    }

}
