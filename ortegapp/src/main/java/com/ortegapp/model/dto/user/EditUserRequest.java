package com.ortegapp.model.dto.user;

import com.ortegapp.model.dto.producto.EditProducto;
import com.ortegapp.model.dto.producto.ProductoResponse;
import com.ortegapp.validation.annotation.PhoneNumber;
import com.ortegapp.validation.annotation.StrongPassword;
import com.ortegapp.validation.annotation.UniqueEmail;
import com.ortegapp.validation.annotation.UniqueUsername;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class EditUserRequest {
    @UniqueUsername(message = "{newUserDto.username.unique}")
    @NotEmpty(message = "{newUserDto.username.notempty}")
    private String username;
    @StrongPassword
    @NotEmpty(message = "{userDto.password.notempty}")
    private String password;
    @StrongPassword
    @NotEmpty(message = "{userDto.password.notempty}")
    private String verifyPassword;
    @NotEmpty(message = "{newUserDto.email.notempty}")
    @UniqueEmail
    @Email(message = "{newUserDto.email.email}")
    private String email;
    @PhoneNumber
    @NotEmpty
    private String telefono;

    //@URL(message = "{userDto.avatar.url}")
    private String avatar;
    @NotEmpty(message = "{userDto.fullname.notempty}")
    private String fullName;


    public static UserResponse toProductoResponse(EditUserRequest editUserRequest){
        return UserResponse.builder()
                .username(editUserRequest.getUsername())
                .fullName(editUserRequest.getFullName())
                .email(editUserRequest.getEmail())
                .avatar(editUserRequest.getAvatar())
                .build();
    }
}
