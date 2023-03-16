package com.example.testtaskproxysellers.services;

import com.example.testtaskproxysellers.dto.UserDto;
import com.example.testtaskproxysellers.models.User;
import com.example.testtaskproxysellers.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public User getUserByUsername(String name) {
        return userRepository.findUserByUsername(name);
    }

    public void save(UserDto userDto) {
        User user = User.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .build();
        userRepository.save(user);
        logger.info("saved: "+user);
    }

    public boolean addUser(UserDto userDto, Model model, BindingResult result) {
        User userFromDb = userRepository.findUserByUsername(userDto.getUsername());
        if(userFromDb != null) {
            result.rejectValue("username", null, "This username is already used !");
        }

        if(result.hasErrors()) {
            model.addAttribute("user", userDto);
            return false;
        }
        save(userDto);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getUsername(),
                    user.getPassword(),
                    Collections.emptySet());
        }else{
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }
}
