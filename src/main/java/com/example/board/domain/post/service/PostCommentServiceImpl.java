package com.example.board.domain.post.service;


import com.example.board.domain.member.entity.Member;
import com.example.board.domain.member.exception.NotExistMemberException;
import com.example.board.domain.member.repository.MemberRepository;
import com.example.board.domain.post.dto.request.PostCommentRequest;
import com.example.board.domain.post.dto.response.PostCommentResponse;
import com.example.board.domain.post.entity.Post;
import com.example.board.domain.post.entity.PostComment;
import com.example.board.domain.post.exception.NotExistPostException;
import com.example.board.domain.post.repository.PostCommentRepository;
import com.example.board.domain.post.repository.PostRepository;
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
        try {
            Member savedMember = getMember(memberId);

            Post savedPost = postRepository.findById(postCommentRequest.postId())
                    .orElseThrow(() -> new NotExistPostException());

            PostComment newPostComment = postCommentRequest.toEntity(savedPost, savedMember);
            PostComment savedPostComment = postCommentRepository.save(newPostComment);
            savedPostComment.setPost(savedPost);
            return PostCommentResponse.of(savedPostComment);
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotExistMemberException());
    }


}
