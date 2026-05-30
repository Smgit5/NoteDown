package com.suman.notedown.dto.userDtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserRegisterRequestDTO {
    private String username;
    private String password;
    private String gender;
    private LocalDate dob;
}
