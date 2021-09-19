package com.nicat.socailmedia.service;

import com.nicat.socailmedia.domain.Post;
import com.nicat.socailmedia.dto.request.CreatePostRequest;

import java.util.List;

public interface PostService {
    void create(CreatePostRequest request);
    Post findById(Long id);
    List<Post> findAll();
}
