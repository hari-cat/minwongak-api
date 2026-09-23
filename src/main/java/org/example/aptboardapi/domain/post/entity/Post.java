package org.example.aptboardapi.domain.post.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.aptboardapi.common.entity.BaseEntity;
import org.example.aptboardapi.domain.user.entity.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;
    private int readCount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    private Post(String title, String content, Category category, User author) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.author = author;
    }

    public static Post create(String title, String content, Category category, User author) {
        return new Post(title, content, category, author);
    }
}
