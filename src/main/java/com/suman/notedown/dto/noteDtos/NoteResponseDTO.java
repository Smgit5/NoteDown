package com.suman.notedown.dto.noteDtos;

import com.suman.notedown.dto.userDtos.UserResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoteResponseDTO {
    private Integer id;
    private String title;
    private String content;
    private UserResponseDTO owner;
}
