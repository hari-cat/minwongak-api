package org.example.aptboardapi.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.example.aptboardapi.common.entity.Status;
import org.example.aptboardapi.common.exception.BusinessException;
import org.example.aptboardapi.common.exception.ErrorCode;
import org.example.aptboardapi.domain.comment.dto.CreateCommentRequest;
import org.example.aptboardapi.domain.comment.entity.Comment;
import org.example.aptboardapi.domain.comment.repository.CommentRepository;
import org.example.aptboardapi.domain.post.entity.Post;
import org.example.aptboardapi.domain.post.repository.PostRepository;
import org.example.aptboardapi.domain.user.entity.User;
import org.example.aptboardapi.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    @Transactional
    public void createComment(CreateCommentRequest request, Long userId){
        User author = userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        Post post = postRepository.findByIdAndStatus(request.postId(), Status.ACTIVE).orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));

        Comment parent = null;

        if (request.parentId() != null) {
            parent = commentRepository.findById(request.parentId())
                    .orElseThrow(() ->
                            new BusinessException(ErrorCode.COMMENT_NOT_FOUND));

            if (parent.getParent() != null) {
                throw new BusinessException(ErrorCode.COMMENT_DEPTH_EXCEEDED);
            }

            if (!parent.getPost().getId().equals(request.postId())) {
                throw new BusinessException(ErrorCode.COMMENT_POST_MISMATCH);
            }
        }

        Comment comment = Comment.create(request.content(), post, author, parent);
        commentRepository.save(comment);
    }

}
