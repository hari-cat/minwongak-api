package org.example.aptboardapi.domain.comment.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.aptboardapi.common.entity.BaseEntity;
import org.example.aptboardapi.domain.post.entity.Post;
import org.example.aptboardapi.domain.user.entity.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    private Comment(String content, Post post, User author, Comment parent) {
        this.content = content;
        this.post = post;
        this.author = author;
        this.parent = parent;
    }

    public static Comment create(
            String content,
            Post post,
            User author,
            Comment parent
    ) {
        return new Comment(content, post, author, parent);
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
