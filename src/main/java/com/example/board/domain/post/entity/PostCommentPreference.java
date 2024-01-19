package com.example.board.domain.post.entity;

import com.example.board.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostCommentPreference extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_comment_id")
    private PostComment postComment;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public void addPreference(PostComment postComment, Member member) {
        this.postComment = postComment;
        this.member = member;
        postComment.getPostPreferenceList().add(this);
    }

    public void deletePreference(PostComment postComment, Member member) {
        this.postComment = postComment;
        this.member = member;
        postComment.getPostPreferenceList().remove(this);
    }
}
