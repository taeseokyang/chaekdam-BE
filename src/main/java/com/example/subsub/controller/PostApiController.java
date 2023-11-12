package com.example.subsub.controller;

import com.example.subsub.domain.Comment;
import com.example.subsub.domain.Post;
import com.example.subsub.dto.request.AddPostRequest;
import com.example.subsub.dto.request.UpdateCommentRequest;
import com.example.subsub.dto.request.UpdatePostRequest;
import com.example.subsub.dto.response.PostResponse;
import com.example.subsub.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    // 생성
    @PostMapping
    public PostResponse save(@RequestBody AddPostRequest request, Authentication authentication) {
        System.out.println(authentication.getName());
        Post post = postService.save(request, authentication.getName());
        return new PostResponse(post);
    }

    // 조회
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> findById(@PathVariable Integer id) {
        Post subject = postService.getPost(id);
        return ResponseEntity.ok().body(new PostResponse(subject));
    }

    // 모두 조회 by userid
    @GetMapping("/all")
    public ResponseEntity<List<Post>> getAllPostByUserId(Authentication authentication) throws Exception {
        List<Post> posts = postService.getAllPostByUserId(authentication.getName());
        return ResponseEntity.ok(posts);
    }

    // 모두 조회 by location
    @GetMapping("/all/{location}")
    public ResponseEntity<List<Post>> getAllPostByLocation(@PathVariable String location) throws Exception {
        List<Post> posts = postService.getAllPostByLocation(location);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/top5")
    public ResponseEntity<List<Post>> getTop10Post() throws Exception {
        List<Post> posts = postService.getTop10Post();
        return ResponseEntity.ok(posts);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Integer id) {
        ResponseEntity<String> result = postService.deletePost(id);
        return result;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post>  updatePost(@PathVariable Integer id, @RequestBody UpdatePostRequest request) {
        ResponseEntity<Post> post = postService.update(id, request);
        return post;
    }
}
