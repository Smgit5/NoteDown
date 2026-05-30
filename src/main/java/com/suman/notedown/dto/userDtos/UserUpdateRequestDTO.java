package com.suman.notedown.dto.userDtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserUpdateRequestDTO {
    private String username;
    private String gender;
    private LocalDate dob;
}
