package com.suman.notedown.util;

import com.suman.notedown.dto.UserResponseDTO;
import com.suman.notedown.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDTO toDTO(User user);
}
