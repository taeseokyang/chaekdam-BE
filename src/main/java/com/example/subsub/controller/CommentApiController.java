package com.example.subsub.controller;

import com.example.subsub.domain.Comment;
import com.example.subsub.dto.request.AddCommentRequest;
import com.example.subsub.dto.response.CommentResponse;
import com.example.subsub.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;


    @PostMapping
    public CommentResponse save(@RequestBody AddCommentRequest request, Authentication authentication) {
        Comment comment = commentService.save(request, authentication.getName());
        return new CommentResponse(comment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer id) {
        commentService.delete(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/all")
    public ResponseEntity<List<Comment>>  getAllComment() {
        List<Comment> comments = commentService.getAllComment();

        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

//    @PatchMapping("/{id}")
//    public CommentResponse update(@PathVariable Integer id, @RequestBody UpdateCommentRequest request) {
//        Comment comment = commentService.update(id, request);
//        return new CommentResponse(comment);
//    }
}
