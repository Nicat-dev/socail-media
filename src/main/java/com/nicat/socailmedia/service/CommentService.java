package com.nicat.socailmedia.service;

import com.nicat.socailmedia.domain.Comment;
import com.nicat.socailmedia.dto.request.CreateCommentRequest;

import java.util.List;

public interface CommentService {
    void create(CreateCommentRequest request);
    Comment findById(Long id);
    List<Comment> findAll();
    void deleteById(Long id);
    Comment findByAccountId(Long id);
}
