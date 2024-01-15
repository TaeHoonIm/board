package com.example.board.domain.post.entity;

import com.example.board.domain.member.entity.Member;
import com.example.board.domain.post.dto.request.PostRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column
    private int viewCount = 0;

    @ManyToOne
    @JoinColumn(name = "writer")
    private Member member;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostPreference> postPreferenceList = new ArrayList<PostPreference>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostComment> commentList = new ArrayList<PostComment>();



}