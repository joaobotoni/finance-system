package com.example.financeSys.dto;

import com.example.financeSys.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UserDTO {
    private UUID id;
    private String name;
    private String username;

    public UserDTO(User user) {
        id = user.getId();
        name = user.getName();
        username = getUsername();
    }

}
