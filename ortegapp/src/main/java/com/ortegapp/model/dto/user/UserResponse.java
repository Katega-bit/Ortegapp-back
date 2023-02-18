package com.ortegapp.model.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ortegapp.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


import java.time.LocalDateTime;

@Data
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public class UserResponse {



    protected String id,username, avatar, fullName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    protected LocalDateTime createdAt;


    public static UserResponse fromUser(User user) {

        return UserResponse.builder()
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .fullName(user.getFullName())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public static User fromUserResponse(UserResponse user) {

        return User.builder()
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .fullName(user.getFullName())
                .createdAt(user.getCreatedAt())
                .build();
    }


}
