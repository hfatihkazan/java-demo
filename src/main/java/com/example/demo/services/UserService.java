package com.example.demo.services;

import com.example.demo.entities.User;
import com.example.demo.models.UserDto;
import com.example.demo.repositories.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService{
    private IUserRepository _userRepository;

    public UserService(IUserRepository _userRepository) {
        this._userRepository = _userRepository;
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = _userRepository.findAll();
        return users.stream().map((user)-> mapToUserDto(user)).collect(Collectors.toList());
    }

    @Override
    public User saveUser(User user) {
        return _userRepository.save(user);
    }

    @Override
    public User updateUser(UserDto user) {
        User _user = mapToUser(user);
        return _userRepository.save(_user);
    }

    private User mapToUser(UserDto user) {
        User _user = User.builder()
                .id(user.getId())
                .email(user.getEmail())
                .phone(user.getPhone())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .build();
        return _user;
    }

    @Override
    public UserDto findUserById(long userId) {
        User _user = _userRepository.findById(userId).get();
        return mapToUserDto(_user);
    }

    private UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .build();
    }
}
