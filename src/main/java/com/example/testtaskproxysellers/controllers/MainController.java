package com.example.testtaskproxysellers.controllers;

import com.example.testtaskproxysellers.services.NoteService;
import com.example.testtaskproxysellers.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private NoteService noteService;

    @GetMapping("/")
    public String getIndex(@CurrentSecurityContext(expression="authentication?.name")
                               String currentUsername, Model model) {
        model.addAttribute("notes", noteService.convertNotesToDto(noteService.getAllSortedNotes(), currentUsername));
        model.addAttribute("user", userService.getUserByUsername(currentUsername));
        return "index";
    }

}
