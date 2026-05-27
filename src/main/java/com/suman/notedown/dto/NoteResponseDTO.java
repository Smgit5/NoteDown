package com.suman.notedown.dto;

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
