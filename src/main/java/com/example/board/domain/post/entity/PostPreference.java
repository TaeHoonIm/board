package com.example.board.domain.post.entity;

import com.example.board.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostPreference extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public void addPreference(Post post, Member member) {
        this.post = post;
        this.member = member;
        post.getPostPreferenceList().add(this);
    }

    public void deletePreference(Post post, Member member) {
        this.post = post;
        this.member = member;
        post.getPostPreferenceList().remove(this);
    }
}
