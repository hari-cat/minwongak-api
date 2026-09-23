package org.example.aptboardapi.domain.post.service;

import lombok.extern.slf4j.Slf4j;
import org.example.aptboardapi.common.entity.Status;
import org.example.aptboardapi.common.exception.BusinessException;
import org.example.aptboardapi.common.exception.ErrorCode;
import org.example.aptboardapi.domain.post.dto.CreatePostRequest;
import org.example.aptboardapi.domain.post.dto.PostDetailResponse;
import org.example.aptboardapi.domain.post.dto.PostResponse;
import org.example.aptboardapi.domain.post.entity.Post;
import org.example.aptboardapi.domain.post.entity.Category;
import org.example.aptboardapi.domain.post.repository.PostRepository;
import org.example.aptboardapi.domain.user.entity.Role;
import org.example.aptboardapi.domain.user.entity.User;
import org.example.aptboardapi.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @Test
    @DisplayName("게시물 단건조회")
    void getPost() {
        User user = new User(1L, "test", "test1", "894989", Role.USER);
        Long postId = 1L;
        Post post = new Post(postId, "게시물 제목", "게시물 내용", Category.FACILITY, 1, user);
        given(postRepository.findByIdAndStatus(postId, Status.ACTIVE)).willReturn(Optional.of(post));

        PostDetailResponse response = postService.getPost(postId);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(postId);
        assertThat(response.title()).isEqualTo("게시물 제목1");
        assertThat(response.content()).isEqualTo("게시물 내용");
        verify(postRepository).findByIdAndStatus(postId, Status.ACTIVE);

    }

    @Test
    @DisplayName("게시물이 존재하지 않으면 POST_NOT_FOUND 예외 발생")
    void getPost_postNotFound() {
        Long postId = 1L;
        given(postRepository.findByIdAndStatus(postId, Status.ACTIVE)).willReturn(Optional.empty());
        assertThatThrownBy(() -> postService.getPost(postId)).isInstanceOf(BusinessException.class).extracting("errorCode").isEqualTo(ErrorCode.POST_NOT_FOUND);
        verify(postRepository).findByIdAndStatus(postId, Status.ACTIVE);
    }

    @Test
    @DisplayName("게시물 목록 조회")
    void findActivePosts() {
        User user = new User(1L, "test", "test1", "894989", Role.USER);
        Post post = new Post(1L, "제목", "내용", Category.FACILITY, 1, user);
        List<Post> posts = List.of(post);

        given(postRepository.findAllByStatus(Status.ACTIVE)).willReturn(posts);

        List<PostResponse> result = postService.getPosts();

        log.info("result={}, posts={}", result, posts);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().title()).isEqualTo("제목");
        assertThat(result.getFirst().content()).isEqualTo("내용11");

        then(postRepository)
                .should()
                .findAllByStatus(Status.ACTIVE);
    }

    @Test
    @DisplayName("게시물 생성")
    void createPost() {
        User user = new User(1L, "test", "test1", "894989", Role.USER);

        Long userId = 1L;

        CreatePostRequest request = new CreatePostRequest(
                "엘리베이터 고장",
                "101동 엘리베이터가 고장났습니다.",
                Category.FACILITY
        );

        Post post = Post.create(
                request.title(),
                request.content(),
                request.category(),
                user
        );

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(postRepository.save(any(Post.class)))
                .thenReturn(post);

        Long postId = postService.createPost(request, userId);

        assertThat(postId).isEqualTo(post.getId());

        verify(userRepository).findById(userId);
        verify(postRepository).save(any(Post.class));
    }

}