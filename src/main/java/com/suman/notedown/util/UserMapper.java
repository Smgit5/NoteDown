package com.suman.notedown.util;

import com.suman.notedown.dto.userDtos.UserRegisterRequestDTO;
import com.suman.notedown.dto.userDtos.UserResponseDTO;
import com.suman.notedown.dto.userDtos.UserUpdateRequestDTO;
import com.suman.notedown.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.context.annotation.Bean;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDTO toDTO(User user);

    User toEntity(UserRegisterRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toEntity(UserUpdateRequestDTO dto, @MappingTarget User user);
}
