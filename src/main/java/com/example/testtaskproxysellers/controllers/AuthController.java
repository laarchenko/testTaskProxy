package com.example.testtaskproxysellers.controllers;

import com.example.testtaskproxysellers.dto.UserDto;
import com.example.testtaskproxysellers.repositories.UserRepository;
import com.example.testtaskproxysellers.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", UserDto.builder().build());
        return "register";
    }

    @PostMapping("/register")
    public String addUser(@Valid @ModelAttribute("user") UserDto userDto, Model model, BindingResult result) {

        boolean isSuccessful = userService.addUser(userDto, model, result);

        if(isSuccessful) {
            return "redirect:/login";
        }
        else {
            return "register";
        }
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

}
