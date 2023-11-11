package com.example.subsub.service;

import com.example.subsub.domain.Post;
import com.example.subsub.domain.User;
import com.example.subsub.dto.request.AddPostRequest;
import com.example.subsub.repository.PostRepository;
import com.example.subsub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public List<Post> getAllPost(String userid) {
        User user = userRepository.findByUserId(userid).get();
        return postRepository.findAllByUser(user);
    }

    //삭제
    public void deletePost(Integer id){
        postRepository.deleteById(id);
    }

}