package eggis0.baram.domain.post.application;

import eggis0.baram.domain.count.domain.VisitCount;
import eggis0.baram.domain.count.repository.VisitCountRepository;
import eggis0.baram.domain.image.application.ImageService;
import eggis0.baram.domain.post.domain.Post;
import eggis0.baram.domain.post.dto.req.AddPostRequest;
import eggis0.baram.domain.post.dto.req.UpdatePostRequest;
import eggis0.baram.domain.post.dto.res.PostResponse;
import eggis0.baram.domain.post.dto.res.PostsResponse;
import eggis0.baram.domain.post.exception.PostNotFoundException;
import eggis0.baram.domain.post.repository.PostRepository;
import eggis0.baram.domain.user.domain.User;
import eggis0.baram.domain.user.exception.UserNotFountException;
import eggis0.baram.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {


    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final VisitCountRepository visitCountRepository;
    private final ImageService imageService;

    public PostResponse save(AddPostRequest request, String userName, MultipartFile pic) {
        User user = userRepository.findByUserId(userName).get();

        String imageFileName = imageService.save(pic);
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .location(request.getLocation())
                .locationDetail(request.getLocationDetail())
                .rentalFee(request.getRentalFee())
                .security(request.getSecurity())
                .createdAt(LocalDateTime.now())
                .needAt(request.getNeedAt())
                .returnAt(request.getReturnAt())
                .isClose(false)
                .isLenderWriteReview(false)
                .user(user)
                .imgPath(imageFileName)
                .build();
        Post savedPost = postRepository.save(post);
        return new PostResponse(savedPost);
    }

    public PostResponse get(Integer id) {
        if (!postRepository.existsByPostId(id)) {
            throw new PostNotFoundException();
        }
        Post post = postRepository.findById(id).get();
        return new PostResponse(post);
    }

    public List<PostsResponse> get3ByUser(String userId) {
        if (!userRepository.existsUserByUserId(userId)) {
            throw new UserNotFountException();
        }
        User user = userRepository.findById(userId).get();

        List<PostsResponse> postsDTO = new ArrayList<>();
        List<Post> posts = postRepository.findTop3ByUserOrderByCreatedAtDesc(user);

        for (Post post : posts) {
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

    public List<PostsResponse> getAllByLocation(String location) {
        List<PostsResponse> postsDTO = new ArrayList<>();
        List<Post> posts = postRepository.findAllByLocationOrderByIsCloseAscCreatedAtDesc(location);
        for (Post post : posts) {
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
        List<Post> posts = postRepository.findAllByLocationStartingWithOrderByCreatedAtDesc(campus.equals("global") ? "G" : "M");
        for (Post post : posts) {
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

    public List<PostsResponse> get8(String campus) {
        List<PostsResponse> postsDTO = new ArrayList<>();
        List<Post> posts = postRepository.findTop8ByLocationStartingWithOrderByCreatedAtDesc(campus.equals("global") ? "G" : "M");
        for (Post post : posts) {
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

        if (visitCountRepository.existsByDay(today)) {
            VisitCount visitCount = visitCountRepository.findByDay(today);
            visitCount.setCount(visitCount.getCount() + 1);
            visitCountRepository.save(visitCount);
            return postsDTO;
        }
        VisitCount visitCount = VisitCount.builder()
                .count(1)
                .day(today)
                .build();
        visitCountRepository.save(visitCount);
        return postsDTO;
    }

    public void delete(Integer postId) {
        if (!postRepository.existsById(postId)) {
            throw new PostNotFoundException();
        }
        postRepository.deleteByPostId(postId);
    }

    public void update(Integer postId, UpdatePostRequest request) {
        if (!postRepository.existsById(postId)) {
            throw new PostNotFoundException();
        }
        Post post = postRepository.findById(postId).get();
        post.setTitle(request.getTitle());
        post.setLocation(request.getLocation());
        post.setLocationDetail(request.getLocationDetail());
        post.setNeedAt(request.getNeedAt());
        post.setReturnAt(request.getReturnAt());
        post.setRentalFee(request.getRentalFee());
        post.setSecurity(request.getSecurity());
        postRepository.save(post);
    }

    public void doneAndCount(Integer postId, String lenderId, String borrowerId) {
        if (!postRepository.existsById(postId)) {
            throw new PostNotFoundException();
        }
        Post post = postRepository.findById(postId).get();
        post.setIsClose(true);
        postRepository.save(post);

        if (!userRepository.existsUserById(borrowerId)) {
            throw new UserNotFountException();
        }
        User borrower = userRepository.findById(borrowerId).get();
        borrower.setBorrowCount(borrower.getBorrowCount() + 1);
        userRepository.save(borrower);

        if (!userRepository.existsUserById(lenderId)) {
            throw new UserNotFountException();
        }
        User lender = userRepository.findById(lenderId).get();
        borrower.setLendCount(lender.getLendCount() + 1);
        userRepository.save(lender);
    }

    public void done(Integer postId) {
        if (!postRepository.existsById(postId)) {
            throw new PostNotFoundException();
        }
        Post post = postRepository.findById(postId).get();
        post.setIsClose(true);
        postRepository.save(post);
    }
}