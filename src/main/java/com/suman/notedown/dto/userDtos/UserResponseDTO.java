package com.suman.notedown.dto.userDtos;

import com.suman.notedown.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserResponseDTO {
    private Integer id;
    private String username;
    private Role role;
    private String gender;
    private LocalDate dob;
    private boolean enabled;
}
