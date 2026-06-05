package com.suman.notedown.dto.userDtos;

import com.suman.notedown.enums.Gender;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserUpdateRequestDTO {
    @NotNull(message = "Username is required")
    @Pattern(
            regexp = "^[A-Za-z0-9_-]{3,15}$",
            message = "Username must be 3-15 characters long and contain only letters, digits and underscore"
    )
    private String username;
    private Gender gender;

    @NotNull(message = "Date of Birth is required")
    @Past
    private LocalDate dob;
}
