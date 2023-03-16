package com.example.testtaskproxysellers.controllers;

import com.example.testtaskproxysellers.services.NoteService;
import com.example.testtaskproxysellers.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class NoteController {

    @Autowired
    NoteService noteService;

    @Autowired
    UserService userService;

    @PostMapping("/add")
    public String addNewNote(@CurrentSecurityContext(expression="authentication?.name")
                             String currentUsername, @RequestParam String text, Model model) {
        noteService.saveNote(currentUsername, text);
        model.addAttribute("notes", noteService.convertNotesToDto(noteService.getAllSortedNotes(), currentUsername));
        model.addAttribute("user", userService.getUserByUsername(currentUsername));
        return "redirect:/";
    }

    @PostMapping("*/add")
    public String addNewNoteFromNonMainContext(@CurrentSecurityContext(expression="authentication?.name")
                                               String currentUsername, @RequestParam String text, Model model) {
        noteService.saveNote(currentUsername, text);
        model.addAttribute("notes", noteService.convertNotesToDto(noteService.getAllSortedNotes(), currentUsername));
        model.addAttribute("user", userService.getUserByUsername(currentUsername));
        return "redirect:/";
    }

    @PostMapping("/edit")
    public String editAnotherNote(@CurrentSecurityContext(expression="authentication?.name")
                                  String currentUsername, @RequestParam String text, @RequestParam String id, Model model) {
        noteService.updateNote(id, text);
        model.addAttribute("notes", noteService.convertNotesToDto(noteService.getAllSortedNotes(), currentUsername));
        model.addAttribute("user", userService.getUserByUsername(currentUsername));
        return "redirect:/";
    }

    @PostMapping("*/edit")
    public String editNoteFromNonMainContext(@CurrentSecurityContext(expression="authentication?.name")
                               String currentUsername, @RequestParam String text, @RequestParam String id, Model model) {
        noteService.updateNote(id, text);
        model.addAttribute("notes", noteService.convertNotesToDto(noteService.getAllSortedNotes(), currentUsername));
        model.addAttribute("user", userService.getUserByUsername(currentUsername));
        return "redirect:/";
    }

    @GetMapping("/delete/{noteId}")
    public String deleteNote(@CurrentSecurityContext(expression="authentication?.name")
                           String currentUsername, @PathVariable("noteId") String noteId, Model model) {
        noteService.deleteNoteById(noteId);
        model.addAttribute("notes", noteService.convertNotesToDto(noteService.getAllSortedNotes(), currentUsername));
        model.addAttribute("user", userService.getUserByUsername(currentUsername));
        return "redirect:/";
    }

    @GetMapping("/like/{noteId}")
    public String likeNote(@CurrentSecurityContext(expression="authentication?.name")
                               String currentUsername, @PathVariable("noteId") String noteId, Model model) {
        noteService.addOrRemoveLike(noteId, currentUsername);
        model.addAttribute("notes", noteService.convertNotesToDto(noteService.getAllSortedNotes(), currentUsername));
        model.addAttribute("user", userService.getUserByUsername(currentUsername));

        return "redirect:/";
    }
}
