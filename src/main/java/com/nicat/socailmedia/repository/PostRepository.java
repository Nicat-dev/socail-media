package com.nicat.socailmedia.repository;

import com.nicat.socailmedia.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}