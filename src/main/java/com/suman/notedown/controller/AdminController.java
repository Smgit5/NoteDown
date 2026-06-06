package com.suman.notedown.controller;

import com.suman.notedown.dto.userDtos.UserResponseDTO;
import com.suman.notedown.dto.userDtos.UserRoleUpdateDTO;
import com.suman.notedown.dto.userDtos.UserStatusDTO;
import com.suman.notedown.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @Operation(summary = "Role update by admin")
    @PatchMapping("/users/{userId}/role")
    public ResponseEntity<UserResponseDTO> updateRole(@PathVariable Integer userId, @RequestBody UserRoleUpdateDTO roleUpdateDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.updateRole(userId, roleUpdateDTO));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> viewAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.viewAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> viewUser(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.viewUser(id));
    }

    @Operation(summary = "Enable/Disable user accound by admin")
    @PatchMapping("/users/{id}/status")
    public ResponseEntity<String> modifyUserStatus(@PathVariable Integer id, @RequestBody UserStatusDTO userStatusDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.modifyUserStatus(id, userStatusDTO));
    }
}
