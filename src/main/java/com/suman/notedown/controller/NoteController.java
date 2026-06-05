package com.suman.notedown.controller;

import com.suman.notedown.dto.noteDtos.NoteRequestDTO;
import com.suman.notedown.dto.noteDtos.NoteResponseDTO;
import com.suman.notedown.entity.User;
import com.suman.notedown.service.NoteService;
import com.suman.notedown.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping("/all-notes")
    public ResponseEntity<List<NoteResponseDTO>> getAllNotes() {
        return ResponseEntity.status(HttpStatus.OK).body(noteService.getAllNotes());
    }

    @GetMapping("/{noteId}")
    public ResponseEntity<NoteResponseDTO> getNote(@PathVariable Integer noteId) {
        return ResponseEntity.status(HttpStatus.OK).body(noteService.getNote(noteId));
    }

    @PostMapping("/new")
    public ResponseEntity<NoteResponseDTO> createNote(@Valid @RequestBody NoteRequestDTO noteRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(noteService.createNote(noteRequestDTO));
    }

    @PutMapping("/edit/{noteId}")
    public ResponseEntity<NoteResponseDTO> editNote(@PathVariable Integer noteId, @Valid @RequestBody NoteRequestDTO noteRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(noteService.editNote(noteId, noteRequestDTO));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteNote(@RequestBody List<Integer> noteIds) {
        return ResponseEntity.status(HttpStatus.OK).body(noteService.deleteNote(noteIds));
    }
}
