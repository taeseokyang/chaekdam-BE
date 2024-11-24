package chaekdam.domain.user.application;

import chaekdam.domain.user.exception.IncorrectPasswordException;
import chaekdam.domain.user.exception.UserNotFountException;
import chaekdam.domain.user.repository.UserRepository;
import chaekdam.domain.image.application.ImageService;
import chaekdam.domain.oauth.application.OAuthService;
import chaekdam.domain.oauth.domain.KakaoUserInfo;
import chaekdam.domain.user.domain.Role;
import chaekdam.domain.user.domain.User;
import chaekdam.domain.user.dto.req.UserRequest;
import chaekdam.domain.user.dto.res.UserResponse;
import chaekdam.domain.user.exception.DuplicateUserIdException;
import chaekdam.global.config.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.TimeUnit;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final OAuthService oAuthService;
    private final ImageService imageService;
    private final RedisTemplate<String, String> redisTemplate;

    private static final String DEFAULT_IMAGE = "default.png";
    public static final long EXPIRATION_TIME = 60 * 60 * 1000L;
    private static final String EMAIL_KEYWORD = "E";


    public UserResponse kakaoLogin(String code) throws Exception {
        String token = oAuthService.getKakaoAccessToken(code);
        KakaoUserInfo kakaoUserInfo = oAuthService.getKakaoUserInfo(token);

        String email = kakaoUserInfo.getEmail();

        if (!userRepository.existsUserByUserId(email)) {
            redisTemplate.opsForValue().set(
                    EMAIL_KEYWORD + code,
                    email,
                    EXPIRATION_TIME,
                    TimeUnit.MILLISECONDS
            );
            throw new UserNotFountException();
        }
        User user = userRepository.findByUserId(email).get();
        UserResponse signResponse = UserResponse.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .nickname(user.getNickName())
                .roles(user.getRole())
                .token(jwtProvider.createToken(user.getUserId(), user.getRole()))
                .build();
        return signResponse;
    }

    public UserResponse kakaoRegister(String nickname, String code) {
        String email = redisTemplate.opsForValue().get(EMAIL_KEYWORD + code);
        if (userRepository.existsUserByUserId(email)) {
            throw new DuplicateUserIdException();
        }

        User user = User.builder()
                .userId(email)
                .nickName(nickname)
                .imgPath(DEFAULT_IMAGE)
                .role(Role.USER)
                .build();
        userRepository.save(user);
        UserResponse signResponse = UserResponse.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .nickname(user.getNickName())
                .roles(user.getRole())
                .token(jwtProvider.createToken(user.getUserId(), user.getRole()))
                .build();
        return signResponse;
    }


    public UserResponse login(UserRequest request) {
        if (!userRepository.existsUserByUserId(request.getUserid())) {
            throw new UserNotFountException();
        }
        User user = userRepository.findByUserId(request.getUserid()).get();

        if (!user.getPassWord().equals(request.getPassword())) {
            throw new IncorrectPasswordException();
        }
        UserResponse signResponse = UserResponse.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .nickname(user.getNickName())
                .roles(user.getRole())
                .token(jwtProvider.createToken(user.getUserId(), user.getRole()))
                .build();
        return signResponse;
    }

    public void register(UserRequest request, MultipartFile pic) {
        if (userRepository.existsUserByUserId(request.getUserid())) {
            throw new DuplicateUserIdException();
        }
        String imageFileName = imageService.save(pic);
        User user = User.builder()
                .userId(request.getUserid())
                .passWord(request.getPassword())
                .nickName(request.getNickname())
                .imgPath(imageFileName)
                .role(Role.USER)
                .build();
        userRepository.save(user);
    }

    public UserResponse get(Long id) {
        if (!userRepository.existsUserById(id)) {
            throw new UserNotFountException();
        }
        User user = userRepository.findById(id).get();
        return new UserResponse(user);
    }
}