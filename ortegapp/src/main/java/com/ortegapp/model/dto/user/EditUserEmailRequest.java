package com.ortegapp.model.dto.user;

import com.ortegapp.validation.annotation.UniqueUsername;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class EditUserEmailRequest {
    @UniqueUsername(message = "{newUserDto.username.unique}")
    @NotEmpty(message = "{newUserDto.username.notempty}")
    private String username;

    private String email;
    private String telefono;

    //@URL(message = "{userDto.avatar.url}")
    private String avatar;
    @NotEmpty(message = "{userDto.fullname.notempty}")
    private String fullName;


    public static UserResponse toProductoResponse(EditUserEmailRequest editUserEmailRequest){
        return UserResponse.builder()
                .username(editUserEmailRequest.getUsername())
                .fullName(editUserEmailRequest.getFullName())
                .email(editUserEmailRequest.getEmail())
                .avatar(editUserEmailRequest.getAvatar())
                .build();
    }
}
