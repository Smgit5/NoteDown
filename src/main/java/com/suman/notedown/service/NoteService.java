package com.suman.notedown.service;

import com.suman.notedown.dto.noteDtos.NoteRequestDTO;
import com.suman.notedown.dto.noteDtos.NoteResponseDTO;
import com.suman.notedown.entity.Note;
import com.suman.notedown.entity.User;
import com.suman.notedown.enums.Role;
import com.suman.notedown.exception.ResourceNotFoundException;
import com.suman.notedown.repository.NoteRepository;
import com.suman.notedown.util.NoteMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;
    private final UserService userService;

    public NoteService(NoteRepository noteRepository, NoteMapper noteMapper, UserService userService) {
        this.noteRepository = noteRepository;
        this.noteMapper =noteMapper;
        this.userService = userService;
    }

    private boolean isAdminOrOwner(User user, Note note) {
        return user.getRole() == Role.ROLE_ADMIN || note.getOwner().getId().equals(user.getId());
    }

    public NoteResponseDTO createNote(NoteRequestDTO noteRequestDTO) {
        noteRequestDTO.setTitle(noteRequestDTO.getTitle().trim());
        noteRequestDTO.setContent(noteRequestDTO.getContent().trim());
        Note note = noteMapper.toEntity(noteRequestDTO);
        note.setOwner(userService.fetchCurrentUser());
        Note savedNote = noteRepository.save(note);
        return noteMapper.toDTO(savedNote);
    }

    public List<NoteResponseDTO> getAllNotes() {
        User currentUser = userService.fetchCurrentUser();
        List<Note> notes = noteRepository.findByOwnerId(currentUser.getId());
        return notes.stream().map(noteMapper::toDTO).toList();
    }

    public NoteResponseDTO getNote(Integer noteId) {
        User currentUser = userService.fetchCurrentUser();
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("Note not found!"));
        if(!isAdminOrOwner(currentUser, note)) {
            throw new AccessDeniedException("Access Restricted");
        }
        return noteMapper.toDTO(note);
    }

    public NoteResponseDTO editNote(Integer noteId, NoteRequestDTO noteRequestDTO) {
        User currentUser = userService.fetchCurrentUser();
        Note existingNote = noteRepository.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("Note not found!"));
        if(!isAdminOrOwner(currentUser, existingNote)) {
            throw new AccessDeniedException("Access Restricted");
        }
        noteMapper.updateNoteFromDto(noteRequestDTO, existingNote);
        return noteMapper.toDTO(noteRepository.save(existingNote));
    }

    public String deleteNote(List<Integer> noteIds) {
        User currentUser = userService.fetchCurrentUser();
        List<Note> foundNotes = noteRepository.findAllById(noteIds);
        if(foundNotes.size() != noteIds.size()) {
            throw new ResourceNotFoundException("Some notes not found...");
        }
        for(Note note: foundNotes) {
            if(!isAdminOrOwner(currentUser, note)) {
                throw new AccessDeniedException("Access Restricted");
            }
        }
        noteRepository.deleteAll(foundNotes);
        return "Selected notes were successfully deleted.";
    }
}
