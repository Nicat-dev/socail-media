package com.nicat.socailmedia.repository;

import com.nicat.socailmedia.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findAllByAccount_AccountId(Long id);
}