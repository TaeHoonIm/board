package com.example.board.domain.post.entity;

import com.example.board.domain.member.entity.Member;
import com.example.board.domain.post.dto.request.PostRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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

    @Column(length = 50, nullable = false)
    private String category;

    @Column(length = 50, nullable = false)
    private String tag;

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

    public Post updatePost(Post savedPost, Post updatedPost) {
        this.category = updatedPost.getCategory();
        this.tag = updatedPost.getTag();
        this.title = updatedPost.getTitle();
        this.content = updatedPost.getContent();
        this.id = savedPost.getId();
        this.viewCount = savedPost.getViewCount();
        this.setCreatedAt(savedPost.getCreatedAt());
        this.setUpdatedAt(LocalDateTime.now());
        return this;
    }

    public void increaseViewCount() {
        this.viewCount++;
    }
}
