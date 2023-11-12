package com.example.subsub.controller;

import com.example.subsub.domain.Post;
import com.example.subsub.dto.request.AddPostRequest;
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

//    @Operation(summary = "게시물 정보입력하기", description = "게시물과 관련된 정보를 입력하면 올라갑니다. ", tags = {"SubjectAPIController"})
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "입력성공",
//                    content = @Content(schema = @Schema(implementation = PostApiController.class))),
//            @ApiResponse(responseCode = "400", description = "확인되지 않은 접근입니다."),
//            @ApiResponse(responseCode = "404", description = "찾을 수 없습니다."),
//            @ApiResponse(responseCode = "500", description = "서버 오류 발생했습니다.")
//    })


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

//    @Operation(summary = "게시물 정보 삭제", description = "입력된 게시물 정보가 삭제됩니다.", tags = {"Post Controller"})
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "입력성공",
//                    content = @Content(schema = @Schema(implementation = PostApiController.class))),
//            @ApiResponse(responseCode = "400", description = "확인되지 않은 접근입니다."),
//            @ApiResponse(responseCode = "404", description = "찾을 수 없습니다."),
//            @ApiResponse(responseCode = "500", description = "서버 오류 발생했습니다.")
//    })

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Integer id) {
        ResponseEntity<String> result = postService.deletePost(id);
        return result;
    }
}
