package com.example.restblog.dto;

import lombok.Data;

@Data
public class UserDto {
    private String name;
    private String email;
    private String role;
    private String password;

}
