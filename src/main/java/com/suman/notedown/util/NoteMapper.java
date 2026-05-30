package com.suman.notedown.util;

import com.suman.notedown.dto.noteDtos.NoteRequestDTO;
import com.suman.notedown.dto.noteDtos.NoteResponseDTO;
import com.suman.notedown.entity.Note;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface NoteMapper {

    NoteResponseDTO toDTO(Note note);

    Note toEntity(NoteRequestDTO dto);

    void updateNoteFromDto(NoteRequestDTO dto, @MappingTarget Note note);
}
