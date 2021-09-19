package com.nicat.socailmedia.service.impl;

import com.nicat.socailmedia.domain.Account;
import com.nicat.socailmedia.domain.Comment;
import com.nicat.socailmedia.domain.Post;
import com.nicat.socailmedia.dto.request.CreatePostRequest;
import com.nicat.socailmedia.exception.DomainNotFoundException;
import com.nicat.socailmedia.exception.MethodNullArgumentException;
import com.nicat.socailmedia.mapper.PostMapper;
import com.nicat.socailmedia.repository.PostRepository;
import com.nicat.socailmedia.service.AccountService;
import com.nicat.socailmedia.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service("PostService")
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final AccountService accountService;

    public void create(CreatePostRequest request){
        final long accountId = request.getAccountId();
        Account account = accountService.findById(accountId);
        Post post = postMapper.toEntity(request);
        post.setAccount(account);
        account.getPosts().add(post);
        postRepository.save(post);

    }

    @Override
    public Post findById(Long id) {
        if (id == null)
            throw new MethodNullArgumentException("Id can not be null on findById");
        return postRepository.findById(id).orElseThrow(
                ()-> new DomainNotFoundException("Account not found by id : "+id));
    }

    @Override
    public List<Post> findAll() {
        List<Post> posts = postRepository.findAll();
        if (posts.isEmpty())
            throw new DomainNotFoundException("no account avaible");
        return posts;
    }
}
