package com.nicat.socailmedia.mapper;

import com.nicat.socailmedia.domain.Account;
import com.nicat.socailmedia.domain.Post;
import com.nicat.socailmedia.dto.request.CreateAccountRequest;
import com.nicat.socailmedia.dto.response.AccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AccountMapper {
    private final PostMapper postMapper;
    private final CommentMapper commentMapper;

    public Account toEntity(CreateAccountRequest from){
        return Account.builder()
                .fullName(from.getFullName())
                .email(from.getEmail())
                .password(from.getPassword())
                .build();
    }

    public AccountResponse toDto(Account from){
        return AccountResponse.builder()
                .accountId(from.getAccountId())
                .fullName(from.getFullName())
                .email(from.getEmail())
                .password(from.getPassword())
                .postResponses(
                        from.getPosts().stream()
                                .map(postMapper::toDto)
                                .collect(Collectors.toList()))
                .commentResponses(
                        from.getComments().stream()
                                .map(commentMapper::toDto)
                                .collect(Collectors.toList()))
                .status(from.getStatus())
                .createdAt(from.getCreatedAt())
                .updatedAt(from.getUpdatedAt())
                .build();
    }

}
