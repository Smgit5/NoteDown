package com.suman.notedown.dto.userDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserRegisterRequestDTO {
    @NotBlank
    @Size(min = 3, max = 15)
    private String username;

    @NotBlank
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,15}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, one special character and be 8-15 characters long"
    )
    private String password;
    private String gender;
    private LocalDate dob;
}
