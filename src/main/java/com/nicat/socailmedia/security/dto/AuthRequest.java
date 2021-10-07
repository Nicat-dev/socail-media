package com.nicat.socailmedia.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    @Email
    @NotEmpty(message = "Email field must not be empty")
    private String email;
    @Size(min = 6,max = 16,message = "Email field size between 6 - 16 character")
    @NotEmpty(message = "Password field must not be empty")
    private String password;
}
