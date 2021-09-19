package com.nicat.socailmedia.mapper;

import com.nicat.socailmedia.domain.Comment;
import com.nicat.socailmedia.dto.request.CreateCommentRequest;
import com.nicat.socailmedia.dto.response.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapper {
    public Comment toEntity(CreateCommentRequest from){
        return Comment.builder()
                .content(from.getContent())
                .build();
    }
    public CommentResponse toDto(Comment from){
        return CommentResponse.builder()
                .accountId(from.getAccount().getAccountId())
                .commentId(from.getCommentId())
                .postId(from.getPost().getPostId())
                .content(from.getContent())
                .status(from.getStatus())
                .createdAt(from.getCreatedAt())
                .updatedAt(from.getUpdatedAt())
                .build();
    }


}
