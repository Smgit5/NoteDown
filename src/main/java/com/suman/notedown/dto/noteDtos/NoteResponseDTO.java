package com.suman.notedown.dto.noteDtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.suman.notedown.dto.userDtos.UserResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NoteResponseDTO {
    private Integer id;
    private String title;
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    private UserResponseDTO owner;
}
