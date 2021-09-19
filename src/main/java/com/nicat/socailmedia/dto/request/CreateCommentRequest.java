package com.nicat.socailmedia.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentRequest {
    @NotEmpty(message = "comment can not be empty")
    private String content;
    @Min(value = 1,message = "Post Id can not be negative")
    private Long postId;
    @Min(value = 1,message = "Account Id can not be negative")
    private Long accountId;
}
