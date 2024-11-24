package chaekdam.domain.user.application;

import chaekdam.domain.user.dto.res.CheckResponse;
import chaekdam.domain.user.exception.IncorrectPasswordException;
import chaekdam.domain.user.exception.UserNotFountException;
import chaekdam.domain.user.repository.UserRepository;
import chaekdam.domain.image.application.ImageService;
import chaekdam.domain.user.domain.Role;
import chaekdam.domain.user.domain.User;
import chaekdam.domain.user.dto.req.UserRequest;
import chaekdam.domain.user.dto.res.UserResponse;
import chaekdam.domain.user.exception.DuplicateUserIdException;
import chaekdam.global.config.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final ImageService imageService;

    // 로그인
    public UserResponse login(UserRequest request) {
        if (!userRepository.existsUserByUserId(request.getId())) throw new UserNotFountException();
        User user = userRepository.findByUserId(request.getId()).get();
        if (!user.getPassWord().equals(request.getPassword())) throw new IncorrectPasswordException();
        UserResponse signResponse = UserResponse.builder()
                .userId(user.getUserId())
                .nickname(user.getNickName())
                .imgPath(user.getImgPath())
                .token(jwtProvider.createToken(user.getUserId(), user.getRole()))
                .build();
        return signResponse;
    }

    // 회원가입
    public UserResponse register(UserRequest request, MultipartFile pic) {
        if (userRepository.existsUserByUserId(request.getId())) throw new DuplicateUserIdException();
        String imageFileName = imageService.save(pic);
        User user = User.builder()
                .userId(request.getId())
                .passWord(request.getPassword())
                .nickName(request.getNickname())
                .imgPath(imageFileName)
                .role(Role.USER)
                .build();
        user = userRepository.save(user);

        UserResponse signResponse = UserResponse.builder()
                .userId(user.getUserId())
                .nickname(user.getNickName())
                .imgPath(user.getImgPath())
                .token(jwtProvider.createToken(user.getUserId(), user.getRole()))
                .build();
        return signResponse;
    }

    // 계정 존재 확인
    public CheckResponse check(String id) {
        if (userRepository.existsUserByUserId(id)) return new CheckResponse(1);
        return new CheckResponse(2);
    }

    // 계정 조회
    public UserResponse get(Long id) {
        if (!userRepository.existsUserById(id)) throw new UserNotFountException();
        User user = userRepository.findById(id).get();
        return new UserResponse(user);
    }
}