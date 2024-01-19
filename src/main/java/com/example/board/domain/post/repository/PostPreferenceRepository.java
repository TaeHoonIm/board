package com.example.board.domain.post.repository;

import com.example.board.domain.post.entity.PostPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostPreferenceRepository extends JpaRepository<PostPreference, Long> {

    Optional<PostPreference> findByMemberIdAndPostId(Long memberId, Long postId);
}
