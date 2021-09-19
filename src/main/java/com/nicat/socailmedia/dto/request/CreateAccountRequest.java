package com.nicat.socailmedia.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountRequest {
    @NotEmpty(message = "Account full name can not be blank")
    @Size(min = 2,max = 100,message = "Name")
    private String fullName;
    @Email(message = "Email is not valid")
    @NotEmpty(message = "Email cannot be empty")
    private String email;
    @NotEmpty(message = "password can not be empty")
    @Size(min = 8, max = 16, message = "Password must be between 8 and 16 characters long")
    private String password;
}
