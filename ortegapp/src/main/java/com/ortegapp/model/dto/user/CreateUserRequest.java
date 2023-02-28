package com.ortegapp.model.dto.user;

import com.ortegapp.validation.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "password", fieldMatch = "verifyPassword",
                message = "{newUserDto.password.nomatch}"
        )
})
public class CreateUserRequest {
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

}
