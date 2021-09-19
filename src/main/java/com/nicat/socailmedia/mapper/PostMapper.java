package com.nicat.socailmedia.mapper;

import com.nicat.socailmedia.domain.Post;
import com.nicat.socailmedia.dto.request.CreatePostRequest;
import com.nicat.socailmedia.dto.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostMapper {

    private final CommentMapper commentMapper;


    public Post toEntity(CreatePostRequest from) {
        return Post.builder()
                .content(from.getContent())
                .build();
    }

    public PostResponse toDto(Post from){
        return PostResponse.builder()
                .postId(from.getPostId())
                .accountId(from.getAccount().getAccountId())
                .contents(from.getContent())
                .respComments(
                        from.getComments().stream()
                                .map(commentMapper::toDto)
                                .collect(Collectors.toList())
                )
                .status(from.getStatus())
                .createdAt(from.getCreatedAt())
                .updatedAt(from.getUpdatedAt())
                .build();
    }

}
