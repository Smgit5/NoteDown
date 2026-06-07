package com.suman.notedown.controller;

import com.suman.notedown.dto.errorDtos.ErrorResponseDTO;
import com.suman.notedown.dto.pageDtos.PageResponseDTO;
import com.suman.notedown.dto.userDtos.UserResponseDTO;
import com.suman.notedown.dto.userDtos.UserRoleUpdateDTO;
import com.suman.notedown.dto.userDtos.UserStatusDTO;
import com.suman.notedown.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@ApiResponses(
        {
                @ApiResponse(responseCode = "403", description = "Access Denied. Only Admins can access", content = @Content(
                        schema = @Schema(implementation = ErrorResponseDTO.class)
                ))
        }
)
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
    public ResponseEntity<PageResponseDTO<UserResponseDTO>> viewAllUsers(
            @ParameterObject
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.viewAllUsers(pageable));
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
