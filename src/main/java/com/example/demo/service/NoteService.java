package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.CustomUser;
import com.example.demo.entity.Note;
import com.example.demo.repository.NoteRepository;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserService userService;

    public void create(String title, String body) {
        Note note = new Note();
        note.setTitle(title);
        note.setBody(body);
        note.setUser(getLoggedUser());
        noteRepository.save(note);
    }
    
    @PostAuthorize("returnObject.getUser().username == authentication.principal.username")
    public Note read(long id) {
        return noteRepository.findById(id).orElseThrow();
    }
    
    @PreAuthorize("this.read(#id).getUser().getUsername() == authentication.principal.username")
    public Note update(long id, String title, String body) {
        Note note = read(id);
        boolean titleHasChanged = !title.equals(note.getTitle());
        boolean bodyHasChanged = !body.equals(note.getBody());

        if (titleHasChanged) {
            note.setTitle(title);
        }
        
        if (bodyHasChanged) {
            note.setBody(body);
        }
        
        if (titleHasChanged || bodyHasChanged) {
            noteRepository.save(note);
        }
        
        return note;
    }
    
    @PreAuthorize("this.read(#id).getUser().getUsername() == authentication.principal.username")
    public void delete(long id) {
        noteRepository.deleteById(id);
    }

    public List<Note> filterByLoggedUser() {
        return noteRepository.findByUser(getLoggedUser());
    }

    public List<Note> filterByQueryString(String queryString) {
        List<Note> notes = noteRepository.findByTitleContainingOrBodyContainingAndUser(queryString, queryString, getLoggedUser());
        return notes;
    }

    public List<Note> bulkAction(int action, int[] noteId) {        
        for (int i = 0; i < noteId.length; i++) {
            switch (action) {
                case 1:
                    this.delete(noteId[i]);
                    break;                
            }

        }
        return this.filterByLoggedUser();
    }

    private CustomUser getLoggedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();        
        CustomUser customUser = userService.read(username);
        return customUser;
    }
        
}
