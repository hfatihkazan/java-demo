package com.example.demo.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
}
