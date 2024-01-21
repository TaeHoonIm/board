package com.example.board.domain.post.service;


import com.example.board.domain.member.entity.Member;
import com.example.board.domain.member.exception.NotExistMemberException;
import com.example.board.domain.member.repository.MemberRepository;
import com.example.board.domain.post.dto.response.PreferenceResponse;
import com.example.board.domain.post.entity.Post;
import com.example.board.domain.post.entity.PostComment;
import com.example.board.domain.post.entity.PostCommentPreference;
import com.example.board.domain.post.entity.PostPreference;
import com.example.board.domain.post.exception.NotExistPostCommentException;
import com.example.board.domain.post.exception.NotExistPostException;
import com.example.board.domain.post.repository.PostCommentPreferenceRepository;
import com.example.board.domain.post.repository.PostCommentRepository;
import com.example.board.domain.post.repository.PostPreferenceRepository;
import com.example.board.domain.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class PreferenceServiceV0 implements PreferenceService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostCommentRepository postCommentRepository;

    @Autowired
    private PostPreferenceRepository postPreferenceRepository;

    @Autowired
    private PostCommentPreferenceRepository postCommentPreferenceRepository;

    @Override
    public PreferenceResponse togglePostPreference(Long memberId, Long postId) {

        Member savedMember = getMember(memberId);
        Post savedPost = postRepository.findById(postId)
                .orElseThrow(() -> new NotExistPostException());

        Optional<PostPreference> savedPostPreference = postPreferenceRepository.findByMemberIdAndPostId(memberId, postId);

        if (savedPostPreference.isPresent()) {
            postPreferenceRepository.delete(savedPostPreference.get());
            savedPostPreference.get().deletePreference(savedPost, savedMember);
        } else {
            PostPreference newPostPreference = PostPreference.builder()
                    .member(savedMember)
                    .post(savedPost)
                    .build();
            postPreferenceRepository.save(newPostPreference);
            newPostPreference.addPreference(savedPost, savedMember);
        }
        return PreferenceResponse.of(savedPost, savedMember);
    }

    @Override
    public PreferenceResponse togglePostCommentPreference(Long memberId, Long postCommentId) {

        Member savedMember = getMember(memberId);
        PostComment savedPostComment = postCommentRepository.findById(postCommentId)
                .orElseThrow(() -> new NotExistPostCommentException());

        Optional<PostCommentPreference> savedPostCommentPreference = postCommentPreferenceRepository
                .findByMemberIdAndPostCommentId(memberId, postCommentId);

        if (savedPostCommentPreference.isPresent()) {
            postCommentPreferenceRepository.delete(savedPostCommentPreference.get());
            savedPostCommentPreference.get().deletePreference(savedPostComment, savedMember);
        } else {
            PostCommentPreference newPostCommentPreference = PostCommentPreference.builder()
                    .member(savedMember)
                    .postComment(savedPostComment)
                    .build();
            postCommentPreferenceRepository.save(newPostCommentPreference);
            newPostCommentPreference.addPreference(savedPostComment, savedMember);
        }
        return new PreferenceResponse(
                savedPostComment.getId(),
                savedMember.getNickname()
        );
    }

    @Override
    public boolean isPostPreference(Long memberId, Long postId) {
        return postPreferenceRepository.findByMemberIdAndPostId(memberId, postId).isPresent();
    }

    @Override
    public boolean isPostCommentPreference(Long memberId, Long postCommentId) {
        return postCommentPreferenceRepository.findByMemberIdAndPostCommentId(memberId, postCommentId).isPresent();
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotExistMemberException());
    }
}
