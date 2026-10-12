package org.example.aptboardapi.domain.comment.service;

import org.aspectj.lang.annotation.Before;
import org.example.aptboardapi.common.entity.Status;
import org.example.aptboardapi.common.exception.BusinessException;
import org.example.aptboardapi.domain.comment.dto.CreateCommentRequest;
import org.example.aptboardapi.domain.comment.entity.Comment;
import org.example.aptboardapi.domain.comment.repository.CommentRepository;
import org.example.aptboardapi.domain.post.entity.Post;
import org.example.aptboardapi.domain.post.repository.PostRepository;
import org.example.aptboardapi.domain.user.entity.User;
import org.example.aptboardapi.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CommentService commentService;

    @Captor
    ArgumentCaptor<Comment> commentCaptor;

    @Test
    void 댓글_작성(){
        Long postId = 1L;
        Long userId = 1L;
        CreateCommentRequest request = new CreateCommentRequest("내용", postId, null);

        Post post = mock(Post.class);
        User author = mock(User.class);

        when(postRepository.findByIdAndStatus(postId, Status.ACTIVE)).thenReturn(Optional.of(post));
        when(userRepository.findById(userId)).thenReturn(Optional.of(author));

        commentService.createComment(request, userId);

        verify(userRepository).findById(userId);
        verify(postRepository).findByIdAndStatus(postId, Status.ACTIVE);
        verify(commentRepository).save(commentCaptor.capture());

        Comment savedComment = commentCaptor.getValue();

        assertThat(savedComment.getContent()).isEqualTo("내용");
        assertThat(savedComment.getAuthor()).isEqualTo(author);
        assertThat(savedComment.getPost()).isEqualTo(post);
    }

    @Test
    void 대댓글_작성시_부모댓글_못찾는_실패오류(){
        Long postId = 1L;
        Long userId = 1L;
        Long parentId = 2L;
        CreateCommentRequest request = new CreateCommentRequest("내용", postId, parentId);

        Post post = mock(Post.class);
        User author = mock(User.class);

        when(postRepository.findByIdAndStatus(postId, Status.ACTIVE)).thenReturn(Optional.of(post));
        when(userRepository.findById(userId)).thenReturn(Optional.of(author));
        when(commentRepository.findById(parentId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> commentService.createComment(request, userId)).isInstanceOf(BusinessException.class).hasMessage("댓글을 찾을 수 없습니다.");
        verify(commentRepository, never()).save(any(Comment.class));
    }
}