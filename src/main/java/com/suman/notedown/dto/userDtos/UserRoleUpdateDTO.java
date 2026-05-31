package com.suman.notedown.dto.userDtos;

import com.suman.notedown.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRoleUpdateDTO {
    private Role role;
}
