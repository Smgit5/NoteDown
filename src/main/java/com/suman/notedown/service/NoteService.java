package com.suman.notedown.service;

import com.suman.notedown.dto.NoteRequestDTO;
import com.suman.notedown.dto.NoteResponseDTO;
import com.suman.notedown.entity.Note;
import com.suman.notedown.entity.User;
import com.suman.notedown.repository.NoteRepository;
import com.suman.notedown.util.NoteMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

    public NoteService(NoteRepository noteRepository, NoteMapper noteMapper) {
        this.noteRepository = noteRepository;
        this.noteMapper =noteMapper;
    }
    public NoteResponseDTO createNote(NoteRequestDTO noteRequestDTO, User user) {
        Note note = new Note();
        noteMapper.updateNoteFromDto(noteRequestDTO, note);
        note.setOwner(user);
        Note savedNote = noteRepository.save(note);
        return noteMapper.toDTO(savedNote);
    }

    public List<NoteResponseDTO> getAllNotes() {
        List<Note> notes = noteRepository.findAll();
        return notes.stream().map(noteMapper::toDTO).toList();
    }

    public NoteResponseDTO getNote(Integer noteId) {
        Note note = noteRepository.findById(noteId).orElseThrow( () -> new RuntimeException("Note not found!"));
        return noteMapper.toDTO(note);
    }

    public NoteResponseDTO editNote(Integer noteId, NoteRequestDTO noteRequestDTO) {
        Note existingNote = noteRepository.findById(noteId).orElseThrow(() -> new RuntimeException("Note not found!"));
        noteMapper.updateNoteFromDto(noteRequestDTO, existingNote);
        return noteMapper.toDTO(noteRepository.save(existingNote));
    }

    public String deleteNote(List<Integer> noteIds) {
        List<Note> foundNotes = noteRepository.findAllById(noteIds);
        if(foundNotes.size() != noteIds.size()) {
            throw new RuntimeException("Some notes not found...");
        }
        noteRepository.deleteAll(foundNotes);
        return "Selected notes were successfully deleted";
    }
}
