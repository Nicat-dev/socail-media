package com.nicat.socailmedia.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostRequest {
    @NotEmpty(message = "content can not be empty")
    private String content;
    @Min(value = 1,message = "account id can not be negative")
    private Long accountId;
}
