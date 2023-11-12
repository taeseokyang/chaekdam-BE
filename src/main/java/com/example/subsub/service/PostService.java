package com.example.subsub.service;

import com.example.subsub.domain.Comment;
import com.example.subsub.domain.Post;
import com.example.subsub.domain.User;
import com.example.subsub.dto.request.AddPostRequest;
import com.example.subsub.dto.request.UpdateCommentRequest;
import com.example.subsub.dto.request.UpdatePostRequest;
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
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 생성
    public Post save(AddPostRequest request, String userName) {
        User user = userRepository.findByUserId(userName).get();
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .location(request.getLocation())
                .locationDetail(request.getLocationDetail())
                .rentalFee(request.getRentalFee())
                .createdAt(request.getCreatedAt())
                .needAt(request.getNeedAt())
                .returnAt(request.getReturnAt())
                .isClose(request.getIsClose())
                .user(user)
                .build();
        return postRepository.save(post);
    }

    // 조회
    public Post getPost(Integer id) {
        return postRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    // 모두 조회
    // 댓글 개수만 반환하게 해야함.
    public List<Post> getAllPostByUserId(String userId) {
        User user = userRepository.findByUserId(userId).get();
        return postRepository.findAllByUser(user);
    }

    // 모두 조회
    // 댓글 개수만 반환하게 해야함.
    public List<Post> getAllPostByLocation(String location) {
        return postRepository.findAllByLocation(location);
    }

    public List<Post> getTop10Post() {
        return postRepository.findTop10ByOrderByCreatedAtDesc();
    }

    //삭제
    @Transactional
    public ResponseEntity<String> deletePost(Integer postId){
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found with ID: " + postId);
        }
        postRepository.deleteByPostId(postId);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post is deleted with ID:" + postId);
    }

    @Transactional
    public ResponseEntity<Post> update(Integer id, UpdatePostRequest request) {
        Post post = postRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        post.setTitle(request.getTitle());
        post.setLocation(request.getLocation());
        post.setLocationDetail(request.getLocationDetail());
        post.setNeedAt(request.getNeedAt());
        post.setReturnAt(request.getReturnAt());
        post.setRentalFee(request.getRentalFee());
        Post updatedPost = postRepository.save(post);
        return ResponseEntity.ok(updatedPost);
    }
}