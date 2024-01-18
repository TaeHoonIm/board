package com.example.board.domain.post.entity;

import com.example.board.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PostComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "postComment", cascade = CascadeType.ALL)
    private List<CommentPreference> postPreferenceList = new ArrayList<CommentPreference>();

    public void setPost(Post post) {
        this.post = post;
        post.getCommentList().add(this);
    }

    public void updatedPostComment(PostComment savedPostComment, PostComment updatedPostComment) {
        this.content = updatedPostComment.getContent();
        this.id = savedPostComment.getId();
        this.setCreatedAt(savedPostComment.getCreatedAt());
        this.setUpdatedAt(LocalDateTime.now());
    }
}
