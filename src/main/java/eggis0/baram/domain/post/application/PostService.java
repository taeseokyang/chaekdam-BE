package eggis0.baram.domain.post.application;

import eggis0.baram.domain.post.dto.res.PostsResponse;
import eggis0.baram.domain.post.dto.req.UpdatePostRequest;
import eggis0.baram.domain.post.domain.Post;
import eggis0.baram.domain.post.dto.req.AddPostRequest;
import eggis0.baram.domain.post.dto.res.PostResponse;
import eggis0.baram.domain.post.repository.PostRepository;
import eggis0.baram.domain.user.domain.User;
import eggis0.baram.domain.visit_count.domain.VisitCount;
import eggis0.baram.domain.user.repository.UserRepository;
import eggis0.baram.domain.visit_count.repository.VisitCountRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    @Value("${image.path}")
    private String IMAGE_PATH;

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final VisitCountRepository visitCountRepository;

    // 생성
    public Post save(AddPostRequest request, String userName, MultipartFile pic) {
        User user = userRepository.findByUserId(userName).get();

        String imageFileName = "default.png";
        if(pic!=null){
            UUID uuid = UUID.randomUUID();
            imageFileName = uuid+"_"+pic.getOriginalFilename();
            Path imagePath = Paths.get(IMAGE_PATH+imageFileName);
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
                .isLenderWriteReview(false)
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
    public List<PostsResponse> get3PostByUser(String userId) {
        List<PostsResponse> postsDTO = new ArrayList<>();

        User user = userRepository.findById(userId).get();
        List<Post> posts = postRepository.findTop3ByUserOrderByCreatedAtDesc(user);

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

    public List<PostsResponse> getAllPostByCampus(String campus) {
        List<PostsResponse> postsDTO = new ArrayList<>();
        List<Post> posts = postRepository.findAllByLocationStartingWithOrderByCreatedAtDesc( campus.equals("global") ? "G" : "M");
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

    public List<PostsResponse> getTop8PostByCampus(String campus) {

        List<PostsResponse> postsDTO = new ArrayList<>();
        List<Post> posts = postRepository.findTop8ByLocationStartingWithOrderByCreatedAtDesc( campus.equals("global") ? "G" : "M");
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

        LocalDate today = LocalDate.now();
        if (visitCountRepository.existsByDay(today)){
            VisitCount visitCount = visitCountRepository.findByDay(today);
            visitCount.setCount(visitCount.getCount()+1);
            visitCountRepository.save(visitCount);
        }else{
            VisitCount visitCount = VisitCount.builder()
                    .count(1)
                    .day(today)
                    .build();
            visitCountRepository.save(visitCount);
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

    public ResponseEntity<PostResponse> doneAndCount(Integer id, String lenderId, String borrowerId) {
        Post post = postRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        post.setIsClose(true);
        Post donePost = postRepository.save(post);

        User borrower = userRepository.findById(borrowerId).get();
        borrower.setBorrowCount(borrower.getBorrowCount()+1);
        userRepository.save(borrower);

        User lender = userRepository.findById(lenderId).get();
        borrower.setLendCount(lender.getLendCount()+1);
        userRepository.save(lender);

        return ResponseEntity.ok(new PostResponse(donePost));
    }

    public ResponseEntity<PostResponse> done(Integer id) {
        Post post = postRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        post.setIsClose(true);
        Post donePost = postRepository.save(post);

        return ResponseEntity.ok(new PostResponse(donePost));
    }
}