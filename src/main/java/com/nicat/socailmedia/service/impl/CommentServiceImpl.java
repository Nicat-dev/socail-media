package com.nicat.socailmedia.service.impl;

import com.nicat.socailmedia.domain.Account;
import com.nicat.socailmedia.domain.Comment;
import com.nicat.socailmedia.domain.Post;
import com.nicat.socailmedia.dto.request.CreateCommentRequest;
import com.nicat.socailmedia.exception.DomainNotFoundException;
import com.nicat.socailmedia.exception.MethodNullArgumentException;
import com.nicat.socailmedia.mapper.CommentMapper;
import com.nicat.socailmedia.repository.CommentRepository;
import com.nicat.socailmedia.service.AccountService;
import com.nicat.socailmedia.service.CommentService;
import com.nicat.socailmedia.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service("CommentService")
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final AccountService accountService;
    private final CommentMapper commentMapper;
    private final PostService postService;

    @Override
    @Transactional
    public void create(CreateCommentRequest request){
        final long postId = request.getPostId();
        Post post = postService.findById(postId);
        final long accountId = request.getAccountId();
        Account account = accountService.findById(accountId);

        Comment comment = commentMapper.toEntity(request);

        comment.setPost(post);
        comment.setAccount(account);

        post.getComments().add(comment);
        account.getComments().add(comment);

        commentRepository.save(comment);
    }

    @Override
    public Comment findById(Long id) {
        if (id == null)
            throw new MethodNullArgumentException("Id can not be null on findById");
        return commentRepository.findById(id).orElseThrow(
                ()-> new DomainNotFoundException("Account not found by id : "+id));
    }

    @Override
    public List<Comment> findAll() {
        List<Comment> comments = commentRepository.findAll();
        if (comments.isEmpty())
            throw new DomainNotFoundException("no account avaible");
        return comments;
    }

    @Override
    public void deleteById(Long id) {
            if (id==null)
                throw new MethodNullArgumentException("Id can not be null on deleteById");
            if (!commentRepository.existsById(id))
                throw new DomainNotFoundException("Post not found by id : "+id);
            commentRepository.deleteById(id);
    }

    @Override
    public Comment findByAccountId(Long id) {
        if (id==null)
            throw new MethodNullArgumentException("Id can n ot be null on findByAccountId");
        return null;
    }

}
