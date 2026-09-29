package org.example.aptboardapi.domain.like.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.aptboardapi.domain.post.entity.Post;
import org.example.aptboardapi.domain.user.entity.User;

@Entity
@Getter
@Table(name = "post_likes", uniqueConstraints = {
        @UniqueConstraint(name = "uk_post_like_user_post", columnNames = {"user_id", "post_id"})
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    public PostLike(User user, Post post){
        this.user = user;
        this.post = post;
    }

    public static PostLike create(User user, Post post){
        return new PostLike(user, post);
    }
}
