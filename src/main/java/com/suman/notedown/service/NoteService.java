package com.suman.notedown.service;

import com.suman.notedown.dto.noteDtos.NoteRequestDTO;
import com.suman.notedown.dto.noteDtos.NoteResponseDTO;
import com.suman.notedown.dto.pageDtos.PageResponseDTO;
import com.suman.notedown.entity.Note;
import com.suman.notedown.entity.User;
import com.suman.notedown.enums.Role;
import com.suman.notedown.exception.ResourceNotFoundException;
import com.suman.notedown.repository.NoteRepository;
import com.suman.notedown.util.NoteMapper;
import com.suman.notedown.util.PaginationUtility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        Note note = new Note();
        note.setTitle(noteRequestDTO.getTitle().trim());
        note.setContent(noteRequestDTO.getContent().trim());
        note.setCreatedAt(LocalDateTime.now());
        note.setOwner(userService.fetchCurrentUser());
        Note savedNote = noteRepository.save(note);
        return noteMapper.toDTO(savedNote);
    }

    public PageResponseDTO<NoteResponseDTO> getAllNotes(Pageable pageable) {
        User currentUser = userService.fetchCurrentUser();
        Page<Note> notes = noteRepository.findByOwnerId(currentUser.getId(), pageable);
        Page<NoteResponseDTO> pageOfNoteResponseDtos = notes.map(noteMapper::toDTO);
        return PaginationUtility.toPageResponseDTO(pageOfNoteResponseDtos);
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
        existingNote.setTitle(noteRequestDTO.getTitle().trim());
        existingNote.setContent(noteRequestDTO.getContent().trim());
        existingNote.setUpdatedAt(LocalDateTime.now());
        return noteMapper.toDTO(noteRepository.save(existingNote));
    }

    public String deleteNote(List<Integer> noteIds) {
        User currentUser = userService.fetchCurrentUser();
        List<Note> foundNotes = noteRepository.findAllById(noteIds);
        for(Note note: foundNotes) {
            if(!isAdminOrOwner(currentUser, note)) {
                throw new AccessDeniedException("Access Restricted");
            }
        }
        noteRepository.deleteAll(foundNotes);
        return "Selected notes were successfully deleted.";
    }
}
