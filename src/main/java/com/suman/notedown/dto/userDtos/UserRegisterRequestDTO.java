package com.suman.notedown.dto.userDtos;

import com.suman.notedown.enums.Gender;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserRegisterRequestDTO {
    @NotNull(message = "Username is required")
    @Pattern(
            regexp = "^[A-Za-z0-9_-]{3,15}$",
            message = "Username must be 3-15 characters long and contain only letters, digits and underscore"
    )
    private String username;

    @NotNull(message = "Password is required")
    @Pattern(
            regexp = "^(?=\\S{8,15}$)(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).*$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, one special character and be 8-15 characters long and must not contain whitespace"
    )
    private String password;
    private Gender gender;

    @NotNull(message = "Date of Birth is required")
    @Past
    private LocalDate dob;
}
