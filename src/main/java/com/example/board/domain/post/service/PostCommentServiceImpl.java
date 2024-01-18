package com.example.board.domain.post.service;


import com.example.board.domain.member.entity.Member;
import com.example.board.domain.member.exception.NotExistMemberException;
import com.example.board.domain.member.repository.MemberRepository;
import com.example.board.domain.post.dto.request.PostCommentRequest;
import com.example.board.domain.post.dto.response.PostCommentResponse;
import com.example.board.domain.post.entity.Post;
import com.example.board.domain.post.entity.PostComment;
import com.example.board.domain.post.exception.NotExistPostCommentException;
import com.example.board.domain.post.exception.NotExistPostException;
import com.example.board.domain.post.repository.PostCommentRepository;
import com.example.board.domain.post.repository.PostRepository;
import com.example.board.global.error.exception.HandleAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostCommentServiceImpl {

    @Autowired
    private PostCommentRepository postCommentRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    public PostCommentResponse createComment(Long memberId, PostCommentRequest postCommentRequest) {

        Member savedMember = getMember(memberId);

        Post savedPost = postRepository.findById(postCommentRequest.postId())
                .orElseThrow(() -> new NotExistPostException());

        PostComment newPostComment = postCommentRequest.toEntity(savedPost, savedMember);
        PostComment savedPostComment = postCommentRepository.save(newPostComment);
        savedPostComment.setPost(savedPost);
        return PostCommentResponse.of(savedPostComment);

    }

    public PostCommentResponse updateComment(Long memberId, Long commentId, PostCommentRequest postCommentRequest) {

        Member savedMember = getMember(memberId);

        PostComment savedPostComment = postCommentRepository.findById(commentId)
                .orElseThrow(() -> new NotExistPostCommentException());

        if (!savedPostComment.getMember().equals(savedMember)) {
            throw new HandleAccessException();
        }

        PostComment updatedPostComment = postCommentRequest.toEntity(savedPostComment.getPost(), savedMember);
        updatedPostComment.updatedPostComment(savedPostComment, updatedPostComment);

        return PostCommentResponse.of(updatedPostComment);
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotExistMemberException());
    }

}
