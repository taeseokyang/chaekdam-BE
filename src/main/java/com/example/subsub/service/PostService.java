package com.example.subsub.service;

import com.example.subsub.domain.User;
import com.example.subsub.repository.PostRepository;
import com.example.subsub.repository.UserRepository;
import com.example.subsub.domain.Post;
import com.example.subsub.dto.request.AddPostRequest;
import com.example.subsub.dto.request.UpdatePostRequest;
import com.example.subsub.dto.response.PostResponse;
import com.example.subsub.dto.response.PostsResponse;
import com.example.subsub.utils.FilePath;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 생성
    public Post save(AddPostRequest request, String userName, MultipartFile pic) {
        User user = userRepository.findByUserId(userName).get();

        String imageFileName = "default.png";
        if(pic!=null){
            UUID uuid = UUID.randomUUID();
            imageFileName = uuid+"_"+pic.getOriginalFilename();
            Path imagePath = Paths.get(FilePath.IMAGEPATH+imageFileName);
            try{
                Files.write(imagePath,pic.getBytes());
            }catch (Exception e){

            }
        }

        LocalDateTime createdAt = LocalDateTime.now();
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .location(request.getLocation())
                .locationDetail(request.getLocationDetail())
                .rentalFee(request.getRentalFee())
                .security(request.getSecurity())
                .createdAt(createdAt)
                .needAt(request.getNeedAt())
                .returnAt(request.getReturnAt())
                .isClose(false)
                .user(user)
                .imgPath(imageFileName)
                .build();
        return postRepository.save(post);
    }

    // 조회
    public ResponseEntity<PostResponse> getPost(Integer id) {
        if (postRepository.existsByPostId(id))
            return ResponseEntity.ok().body(new PostResponse(postRepository.findById(id).orElseThrow(IllegalArgumentException::new)));
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
            dto.setSecurity(post.getSecurity());
            dto.setCreatedAt(post.getCreatedAt());
            dto.setClose(post.getIsClose());
            dto.setPostImgPath(post.getImgPath());
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
            dto.setSecurity(post.getSecurity());
            dto.setCreatedAt(post.getCreatedAt());
            dto.setClose(post.getIsClose());
            dto.setPostImgPath(post.getImgPath());
            postsDTO.add(dto);
        }
        return postsDTO;
    }

    public List<PostsResponse> getTop8PostByCampus(String campus) {
        List<PostsResponse> postsDTO = new ArrayList<>();
        List<Post> posts = postRepository.findTop8ByIsCloseAndLocationStartingWithOrderByCreatedAtDesc(false, campus.equals("global") ? "G" : "M");
        for(Post post : posts){
            PostsResponse dto = new PostsResponse();
            dto.setPostId(post.getPostId());
            dto.setUserId(post.getUser().getUserId());
            dto.setTitle(post.getTitle());
            dto.setLocation(post.getLocation());
            dto.setLocationDetail(post.getLocationDetail());
            dto.setRentalFee(post.getRentalFee());
            dto.setSecurity(post.getSecurity());
            dto.setCreatedAt(post.getCreatedAt());
            dto.setClose(post.getIsClose());
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
        post.setSecurity(request.getSecurity());
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