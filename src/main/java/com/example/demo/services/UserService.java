package com.example.demo.services;

import com.example.demo.entities.User;
import com.example.demo.models.UserDto;
import com.example.demo.repositories.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    private IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map((user) -> mapToUserDto(user)).collect(Collectors.toList());
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(UserDto user) {
        User foundUser = mapToUser(user);
        return userRepository.save(foundUser);
    }

    private User mapToUser(UserDto user) {
        User builtUser = User.builder()
                .id(user.getId())
                .email(user.getEmail())
                .phone(user.getPhone())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .build();
        return builtUser;
    }

    @Override
    public UserDto findUserById(long userId) {
        User foundUser = userRepository.findById(userId).get();
        return mapToUserDto(foundUser);
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
