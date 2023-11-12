package com.example.subsub.service;

import com.example.subsub.domain.Comment;
import com.example.subsub.domain.Post;
import com.example.subsub.domain.User;
import com.example.subsub.dto.request.AddCommentRequest;
import com.example.subsub.dto.request.UpdateCommentRequest;
import com.example.subsub.repository.CommentRepository;
import com.example.subsub.repository.PostRepository;
import com.example.subsub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;



    public Comment save(AddCommentRequest request, String userName) {
        User user = userRepository.findByUserId(userName).get();
        Post post = postRepository.findByPostId(request.getPostId()).get();
        Comment property = Comment.builder()
                .text(request.getText())
                .createdAt(request.getCreatedAt())
                .isSecret(request.getIsSecret())
                .user(user)
                .post(post)
                .build();
        return commentRepository.save(property);
    }

    @Transactional
    public ResponseEntity<String> delete(Integer commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found with ID: " + commentId);
        }
        commentRepository.deleteByCommentId(commentId);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment is deleted with ID:" + commentId);
    }

    // 유저에 따른, 포스트에 따른 으로 바꿔야함.
    public List<Comment> getAllComment() {
        return commentRepository.findAll();
    }

    @Transactional
    public ResponseEntity<Comment> update(Integer id, UpdateCommentRequest request) {
        Comment comment = commentRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        comment.setText(request.getText());
        Comment updatedComment = commentRepository.save(comment);
        return ResponseEntity.ok(updatedComment);
    }
}
