package com.example.subsub.service;

import com.example.subsub.domain.Comment;
import com.example.subsub.domain.Post;
import com.example.subsub.domain.User;
import com.example.subsub.dto.request.AddCommentRequest;
import com.example.subsub.dto.request.UpdateCommentRequest;
import com.example.subsub.repository.CommentRepository;
import com.example.subsub.repository.PostRepository;
import com.example.subsub.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void delete(Integer propertyId) {
        commentRepository.deleteById(propertyId);
    }

    public List<Comment> getAllComment() {
        return commentRepository.findAll();
    }

//    @Transactional
//    public Comment update(Integer id, UpdateCommentRequest request) {
//        Comment comment = commentRepository.findById(id).orElseThrow(IllegalArgumentException::new);
//        comment.update(request);
//        return savedProperty;
//    }
}
