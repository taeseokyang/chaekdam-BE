package com.example.subsub.service;

import com.example.subsub.domain.Post;
import com.example.subsub.domain.User;
import com.example.subsub.dto.request.AddPostRequest;
import com.example.subsub.dto.request.UpdatePostRequest;
import com.example.subsub.dto.response.PostResponse;
import com.example.subsub.dto.response.PostsResponse;
import com.example.subsub.repository.PostRepository;
import com.example.subsub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
//    private final CommentRepository commentRepository;

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
                .isClose(false)
                .user(user)
                .build();
        return postRepository.save(post);
    }

    // 조회
    public Post getPost(Integer id) {
        return postRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    // 모두 조회
    public List<PostsResponse> getAllPostByUserId(String userId) {
        List<PostsResponse> postsDTO = new ArrayList<>();

        User user = userRepository.findByUserId(userId).get();
        List<Post> posts = postRepository.findAllByUser(user);

        for(Post post : posts){
            PostsResponse dto = new PostsResponse();
            dto.setPostId(post.getPostId());
            dto.setUserId(post.getUser().getUserId());
            dto.setTitle(post.getTitle());
            dto.setLocation(post.getLocation());
            dto.setLocationDetail(post.getLocationDetail());
            dto.setRentalFee(post.getRentalFee());
            dto.setCreatedAt(post.getCreatedAt());
            dto.setClose(post.getIsClose());
//            dto.setCommentCount(commentRepository.countByPost(post));

            postsDTO.add(dto);
        }
        return postsDTO;
    }

    // 모두 조회
    public List<PostsResponse> getAllPostByLocation(String location) {
        List<PostsResponse> postsDTO = new ArrayList<>();
        List<Post> posts = postRepository.findAllByLocationOrderByIsCloseAscCreatedAtDesc(location);
        for(Post post : posts){
            PostsResponse dto = new PostsResponse();
            dto.setPostId(post.getPostId());
            dto.setUserId(post.getUser().getUserId());
            dto.setTitle(post.getTitle());
            dto.setLocation(post.getLocation());
            dto.setLocationDetail(post.getLocationDetail());
            dto.setRentalFee(post.getRentalFee());
            dto.setCreatedAt(post.getCreatedAt());
            dto.setClose(post.getIsClose());
            //챗 카운트로 바꿔야
//            dto.setCommentCount(commentRepository.countByPost(post));

            postsDTO.add(dto);
        }
        return postsDTO;
    }

    public List<PostsResponse> getTop10Post() {
        List<PostsResponse> postsDTO = new ArrayList<>();
        List<Post> posts = postRepository.findTop10ByIsCloseOrderByCreatedAtDesc(false);
        for(Post post : posts){
            PostsResponse dto = new PostsResponse();
            dto.setPostId(post.getPostId());
            dto.setUserId(post.getUser().getUserId());
            dto.setTitle(post.getTitle());
            dto.setLocation(post.getLocation());
            dto.setLocationDetail(post.getLocationDetail());
            dto.setRentalFee(post.getRentalFee());
            dto.setCreatedAt(post.getCreatedAt());
            dto.setClose(post.getIsClose());
//            dto.setCommentCount(commentRepository.countByPost(post));

            postsDTO.add(dto);
        }
        return postsDTO;
    }

    //삭제
    public ResponseEntity<String> deletePost(Integer postId){
        if (postRepository.existsById(postId)) {
            postRepository.deleteByPostId(postId);
            return ResponseEntity.status(HttpStatus.OK).body("Post is deleted with ID:" + postId);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found with ID: " + postId);
    }

    public ResponseEntity<PostResponse> update(Integer id, UpdatePostRequest request) {
        Post post = postRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        post.setTitle(request.getTitle());
        post.setLocation(request.getLocation());
        post.setLocationDetail(request.getLocationDetail());
        post.setNeedAt(request.getNeedAt());
        post.setReturnAt(request.getReturnAt());
        post.setRentalFee(request.getRentalFee());
        Post updatedPost = postRepository.save(post);
        return ResponseEntity.ok(new PostResponse(updatedPost));
    }

    public ResponseEntity<PostResponse> done(Integer id) {
        Post post = postRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        post.setIsClose(true);
        Post donePost = postRepository.save(post);
        return ResponseEntity.ok(new PostResponse(donePost));
    }
}