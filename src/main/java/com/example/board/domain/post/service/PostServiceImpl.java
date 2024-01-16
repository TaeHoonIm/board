package com.example.board.domain.post.service;

import com.example.board.domain.member.entity.Member;
import com.example.board.domain.member.exception.NotExistMemberException;
import com.example.board.domain.member.repository.MemberRepository;
import com.example.board.domain.post.dto.request.PostRequest;
import com.example.board.domain.post.dto.response.PostListResponse;
import com.example.board.domain.post.dto.response.PostResponse;
import com.example.board.domain.post.entity.Post;
import com.example.board.domain.post.exception.NotExistPostException;
import com.example.board.domain.post.repository.PostRepository;
import com.example.board.global.error.exception.HandleAccessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class PostServiceImpl {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    public PostResponse createPost(Long memberId, PostRequest postRequest) {
        Member savedMember = getMember(memberId);
        Post newPost = postRequest.toEntity(savedMember);
        Post savedPost = postRepository.save(newPost);

        return PostResponse.of(savedPost);
    }

    public PostResponse deletePost(Long memberId, Long postId) {
        Member savedMember = getMember(memberId);
        Post savedPost = postRepository.findById(postId)
                .orElseThrow(() -> new NotExistPostException());

        if (!savedPost.getMember().equals(savedMember)) {
            throw new HandleAccessException();
        }
        postRepository.delete(savedPost);

        return PostResponse.of(savedPost);
    }

    public PostResponse updatePost(Long memberId, Long postId, PostRequest postRequest) {
        Member savedMember = getMember(memberId);
        Post savedPost = postRepository.findById(postId)
                .orElseThrow(() -> new NotExistPostException());

        if (!savedPost.getMember().equals(savedMember)) {
            throw new HandleAccessException();
        }

        Post updatedPost = postRequest.toEntity(savedMember);
        updatedPost.updatePost(savedPost, updatedPost);

        postRepository.save(updatedPost);

        return PostResponse.of(updatedPost);
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long postId) {
        Post savedPost = postRepository.findById(postId)
                .orElseThrow(() -> new NotExistPostException());

        savedPost.increaseViewCount();

        return PostResponse.of(savedPost);
    }

    @Transactional(readOnly = true)
    public Page<PostListResponse> getAllPosts(int page, int size) {
        return postRepository.findAllByOrderByCreatedAtDesc(Pageable.ofSize(size).withPage(page))
                .map(PostListResponse::of);
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotExistMemberException());
    }



}
