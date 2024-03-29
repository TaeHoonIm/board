package com.example.board.domain.post.repository;

import com.example.board.domain.post.entity.PostComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Long> {

    Page<PostComment> findAllByPostIdOrderByCreatedAtDesc(Long postId, Pageable pageable);
}
