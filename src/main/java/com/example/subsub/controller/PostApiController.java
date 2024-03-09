package com.example.subsub.controller;

import com.example.subsub.domain.Post;
import com.example.subsub.dto.request.AddPostRequest;
import com.example.subsub.dto.request.UpdatePostRequest;
import com.example.subsub.dto.request.UserRequest;
import com.example.subsub.dto.response.PostResponse;
import com.example.subsub.dto.response.PostsResponse;
import com.example.subsub.repository.PostRepository;
import com.example.subsub.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    // 생성
    @PostMapping
    public ResponseEntity<PostResponse> save(@RequestPart AddPostRequest request, @RequestPart(required = false) MultipartFile pic, Authentication authentication) {
        if (authentication == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        Post post = postService.save(request, authentication.getName(), pic);
        return ResponseEntity.ok().body(new PostResponse(post));
    }

    // 조회
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> findById(@PathVariable Integer id) {
//        Post post = postService.getPost(id);
        return postService.getPost(id);
    }

    // 모두 조회 by userid
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostsResponse>> get3PostByUser(@PathVariable String userId) throws Exception {
        List<PostsResponse> posts = postService.get3PostByUser(userId);
        return ResponseEntity.ok(posts);
    }

    // 모두 조회 by location
    @GetMapping("/all/location/{location}")
    public ResponseEntity<List<PostsResponse>> getAllPostByLocation(@PathVariable String location) throws Exception {
        List<PostsResponse> posts = postService.getAllPostByLocation(location);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/all/campus/{campus}")
    public ResponseEntity<List<PostsResponse>> getAllPostByCampus(@PathVariable String campus) throws Exception {
        List<PostsResponse> posts = postService.getAllPostByCampus(campus);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/news")
    public ResponseEntity<List<PostsResponse>> getTop8Post(@RequestParam(name = "campus", required = false) String campus) throws Exception {
        List<PostsResponse> posts = postService.getTop8PostByCampus(campus);
        return ResponseEntity.ok(posts);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Integer id) {
        ResponseEntity<String> result = postService.deletePost(id);
        return result;
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse>  updatePost(@PathVariable Integer id, @RequestBody UpdatePostRequest request) {
        ResponseEntity<PostResponse> post = postService.update(id, request);
        return post;
    }

    @PutMapping("/done/{id}/{lenderId}/{borrowerId}")
    public ResponseEntity<PostResponse> doneAndCount(@PathVariable Integer id,@PathVariable String lenderId,@PathVariable String borrowerId) {
        ResponseEntity<PostResponse> post = postService.doneAndCount(id, lenderId, borrowerId);
        return post;
    }

    @PutMapping("/done/{id}")
    public ResponseEntity<PostResponse> done(@PathVariable Integer id) {
        ResponseEntity<PostResponse> post = postService.done(id);
        return post;
    }
}
