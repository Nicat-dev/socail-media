package com.nicat.socailmedia.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAccountRequset {
    @NotBlank(message = "fullName can not be blank")
    private String fullName;
    @NotBlank(message = "email can not be blank")
    private String email;
    @NotBlank(message = "password can not be blank")
    private String password;
}
