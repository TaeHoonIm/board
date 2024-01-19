package com.example.board.domain.post.repository;

import com.example.board.domain.post.entity.PostCommentPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostCommentPreferenceRepository extends JpaRepository<PostCommentPreference, Long> {

    Optional<PostCommentPreference> findByMemberIdAndPostCommentId(Long memberId, Long postCommentId);
}
