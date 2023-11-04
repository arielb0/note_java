package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import com.example.demo.service.NoteService;
import com.example.demo.service.UserService;
import com.example.demo.entity.CustomUser;
import com.example.demo.entity.Note;

@Controller
@RequestMapping("notes")
public class NoteController {
    
    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;
    
    @GetMapping("/create")
    public String createGet() {
        return "createNote";
    }
    
    @PostMapping("/create")
    public String createPost(@RequestParam("title")String title, @RequestParam("body") String body) {
        noteService.create(title, body);
        return "createNote";
    }

    @GetMapping("/{id}")
    public String read(Model model, @PathVariable("id") long id) {
        Note note = noteService.read(id);
        model.addAttribute("note", note);
        return "readNote";
    }

    @GetMapping("/{id}/update")
    public String updateGet(Model model, @PathVariable("id") long id) {
        Note note = noteService.read(id);
        model.addAttribute("note", note);
        return "updateNote";
    }

    @PostMapping("/update")
    public String updatePost(@RequestParam(name="id")Long id, @RequestParam(name="title") String title, @RequestParam(name="body") String body, Model model) {
        Note note = noteService.update(id, title, body);
        model.addAttribute("note", note);
        return "updateNote";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam(name="id") Long id, Model model) {
        noteService.delete(id);
        model.addAttribute("notes", noteService.filterByLoggedUser());
        return "listNotes";
    }

    @GetMapping("")
    public String list(Model model) {
        List<Note> notes = noteService.filterByLoggedUser();
        model.addAttribute("notes", notes);
        model.addAttribute("userId", getLoggedUserId());
        return "listNotes";
    }

    private long getLoggedUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        CustomUser user = userService.read(username);
        return user.getId();
    }

}
