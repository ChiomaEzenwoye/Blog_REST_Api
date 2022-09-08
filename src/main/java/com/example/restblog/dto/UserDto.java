package com.example.restblog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String name;
    private String email;
    private String roles;
    private String password;

}
