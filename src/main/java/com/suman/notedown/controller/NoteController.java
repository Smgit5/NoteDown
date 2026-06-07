package com.suman.notedown.controller;

import com.suman.notedown.dto.noteDtos.NoteRequestDTO;
import com.suman.notedown.dto.noteDtos.NoteResponseDTO;
import com.suman.notedown.dto.pageDtos.PageResponseDTO;
import com.suman.notedown.entity.User;
import com.suman.notedown.service.NoteService;
import com.suman.notedown.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/all-notes")
    public ResponseEntity<PageResponseDTO<NoteResponseDTO>> getAllNotes(
            @ParameterObject
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(noteService.getAllNotes(pageable));
    }

    @GetMapping("/{noteId}")
    public ResponseEntity<NoteResponseDTO> getNote(@PathVariable Integer noteId) {
        return ResponseEntity.status(HttpStatus.OK).body(noteService.getNote(noteId));
    }

    @ApiResponses(
            {
                    @ApiResponse(responseCode = "201", description = "Note created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request")
            }
    )
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

//    @GetMapping("/search")
//    public ResponseEntity<PageResponseDTO<NoteResponseDTO>> searchNoteByKeyword()
}
