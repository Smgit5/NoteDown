package com.suman.notedown.dto.userDtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserResponseDTO {
    private Integer id;
    private String username;
    private String role;
    private String gender;
    private LocalDate dob;
}
