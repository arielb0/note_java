package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.CustomUser;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("users")
public class UserController {
    
    @Autowired
    UserService userService;
    
    @GetMapping("/create")
    public String createGet() {
        return "createUser";
    }

    @PostMapping("/create")
    public String createPost(@RequestParam(name="username") String username, @RequestParam(name="password") String password) {
        userService.create(username, password);
        return "createUser";
    }

    @GetMapping("/{id}")
    public String read(@PathVariable(name="id") Long id, Model model) {
        CustomUser user = userService.read(id);
        
        model.addAttribute("user", user);
        return "readUser";
    }

    @GetMapping("/{id}/update")
    public String updateGet(@PathVariable(name="id") Long id, Model model) {
        CustomUser user = userService.read(id);
        model.addAttribute("user", user);
        return "updateUser";
    }

    @PostMapping("/update")
    public String updatePost(@RequestParam(name="id")  Long id, @RequestParam(name="username") String username
        , @RequestParam(name="password") String password, Model model) {

        CustomUser user = userService.update(id, username, password);
        model.addAttribute("user", user);

        return "updateUser";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam(name="id") Long id) {
        userService.delete(id);
        return "login";
    }
}
