package com.example.board.domain.post.service;

import com.example.board.domain.member.entity.Member;
import com.example.board.domain.member.exception.NotExistMemberException;
import com.example.board.domain.member.repository.MemberRepository;
import com.example.board.domain.post.dto.request.PostRequest;
import com.example.board.domain.post.dto.response.PostResponse;
import com.example.board.domain.post.entity.Post;
import com.example.board.domain.post.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotExistMemberException());
    }

}
