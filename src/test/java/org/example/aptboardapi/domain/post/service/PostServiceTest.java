package org.example.aptboardapi.domain.post.service;

import lombok.extern.slf4j.Slf4j;
import org.example.aptboardapi.common.entity.Status;
import org.example.aptboardapi.domain.post.dto.PostResponse;
import org.example.aptboardapi.domain.post.entity.Post;
import org.example.aptboardapi.domain.post.entity.PostStatus;
import org.example.aptboardapi.domain.post.repository.PostRepository;
import org.example.aptboardapi.domain.user.entity.Role;
import org.example.aptboardapi.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@Slf4j
@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @Test
    @DisplayName("게시물 목록 조회")
    void findActivePosts(){
        User user = new User(1L, "test", "test1","894989", Role.USER);
        Post post = new Post(1L, "제목", "내용", PostStatus.FACILITY,1,user);
        List<Post> posts = List.of(post);

        given(postRepository.findAllByStatus(Status.ACTIVE)).willReturn(posts);

        List<PostResponse> result = postService.getPosts();

        log.info("result={}, posts={}", result, posts);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().title()).isEqualTo("제목");
        assertThat(result.getFirst().content()).isEqualTo("내용");

        then(postRepository)
                .should()
                .findAllByStatus(Status.ACTIVE);
    }
}