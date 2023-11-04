package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

import com.example.demo.entity.Note;
import com.example.demo.service.NoteService;

@RestController
@RequestMapping("/api/notes")
public class NoteApiRestController {
    
    @Autowired
    private NoteService noteService;

    /*
     * RPC method
     
    @GetMapping("/{id}")
    public Note read(@PathVariable(name="id") Long id) {
        Note note = noteService.read(id);
        return note;
    }
    */
    
    /* HATEOAS method
    
    @GetMapping("/{id}")
    public EntityModel<Note> read(@PathVariable(name="id") Long id) {
        Note note = noteService.read(id);
        return EntityModel.of(note, linkTo(methodOn(NoteApiRestController.class).read(id)).withSelfRel(), linkTo(methodOn(NoteApiRestController.class).list()).withRel("notes"));
    }

     */
     
    /*RPC method
    @GetMapping("")
    public List<Note> list() {
        return noteService.filterByLoggedUser();
    }
     */

    /* HATEOAS method 
    @GetMapping("/{id}")
    public CollectionModel<EntityModel<Note>> list() {
        List<EntityModel<Note>> notes = noteService.filterByLoggedUser().stream().map(note -> EntityModel.of(note, ));
    }
     */
    
}
