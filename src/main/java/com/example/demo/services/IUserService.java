package com.example.demo.services;

import com.example.demo.entities.User;
import com.example.demo.models.UserDto;

import java.util.List;

public interface IUserService {
    List<UserDto> findAllUsers();
    User saveUser(User user);
    User updateUser(UserDto user);
    UserDto findUserById(long userId);
}
