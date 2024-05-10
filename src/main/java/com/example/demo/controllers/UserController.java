package com.example.demo.controllers;

import com.example.demo.entities.User;
import com.example.demo.models.UserDto;
import com.example.demo.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class UserController{
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserDto> listUser(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return users;
    }

    @PostMapping(path = "/users", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public User saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PutMapping("/users/{userId}/edit")
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@PathVariable("userId") Long userId,
                           @Validated @ModelAttribute("user") UserDto user,
                           BindingResult result, Model model) {
        try {
            user.setId(userId);
            return userService.updateUser(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong", e);
        }

    }
}
