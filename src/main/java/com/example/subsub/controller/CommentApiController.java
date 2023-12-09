package com.example.subsub.controller;

import com.example.subsub.domain.Comment;
import com.example.subsub.dto.request.AddCommentRequest;
import com.example.subsub.dto.request.UpdateCommentRequest;
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


    @DeleteMapping("/{id}") //삭제 하고 반환값 없음
    public ResponseEntity<String> deleteComment(@PathVariable Integer id) {
        ResponseEntity<String> result = commentService.delete(id);
        return result;
    }


        // DTO로 변환
    @GetMapping("/all")
    public ResponseEntity<List<Comment>>  getAllComment() {
        List<Comment> comments = commentService.getAllComment();

        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/user/all")
    public ResponseEntity<List<Comment>>  getAllCommentByUserId(Authentication authentication) {
        List<Comment> comments = commentService.getAllCommentByUserId(authentication.getName());
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment>  updateComment(@PathVariable Integer id, @RequestBody UpdateCommentRequest request) {
        ResponseEntity<Comment> comment = commentService.update(id, request);
        return comment;
    }
}
