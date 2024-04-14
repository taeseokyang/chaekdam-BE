package eggis0.baram.domain.user.application;

import eggis0.baram.domain.certification.dto.req.UpdateUserCertifiRequest;
import eggis0.baram.domain.image.application.ImageService;
import eggis0.baram.domain.oauth.application.OAuthService;
import eggis0.baram.domain.oauth.domain.KakaoUserInfo;
import eggis0.baram.domain.user.domain.Role;
import eggis0.baram.domain.user.domain.User;
import eggis0.baram.domain.user.dto.req.UpdateUserRequest;
import eggis0.baram.domain.user.dto.req.UserRequest;
import eggis0.baram.domain.user.dto.res.UserResponse;
import eggis0.baram.domain.user.exception.DuplicateUserIdException;
import eggis0.baram.domain.user.exception.IncorrectPasswordException;
import eggis0.baram.domain.user.exception.UserNotFountException;
import eggis0.baram.domain.user.repository.UserRepository;
import eggis0.baram.global.config.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
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
    private static final String PHONE_NUMBER_KEYWORD = "P";


    public UserResponse kakaoLogin(String code) throws Exception {
        String token = oAuthService.getKakaoAccessToken(code);
        KakaoUserInfo kakaoUserInfo = oAuthService.getKakaoUserInfo(token);

        String email = kakaoUserInfo.getEmail();
        String phoneNumber = kakaoUserInfo.getPhoneNumber();

        if (!userRepository.existsUserByUserId(email)) {
            redisTemplate.opsForValue().set(
                    EMAIL_KEYWORD + code,
                    email,
                    EXPIRATION_TIME,
                    TimeUnit.MILLISECONDS
            );

            redisTemplate.opsForValue().set(
                    PHONE_NUMBER_KEYWORD + code,
                    phoneNumber,
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
        String phoneNumber = redisTemplate.opsForValue().get(PHONE_NUMBER_KEYWORD + code);
        if (userRepository.existsUserByUserId(email)) {
            throw new DuplicateUserIdException();
        }

        String id = UUID.randomUUID().toString();
        User user = User.builder()
                .id(id)
                .userId(email)
                .phone(phoneNumber)
                .nickName(nickname)
                .imgPath(DEFAULT_IMAGE)
                .borrowCount(0)
                .lendCount(0)
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
        String imageFileName = imageService.save(pic, false);
        String id = UUID.randomUUID().toString();
        User user = User.builder()
                .id(id)
                .userId(request.getUserid())
                .passWord(request.getPassword())
                .nickName(request.getNickname())
                .imgPath(imageFileName)
                .borrowCount(0)
                .lendCount(0)
                .role(Role.MANAGER)
                .build();
        userRepository.save(user);
    }

    public UserResponse get(String id) {
        if (!userRepository.existsUserById(id)) {
            throw new UserNotFountException();
        }
        User user = userRepository.findById(id).get();
        return new UserResponse(user);
    }

    public UserResponse updateNicknameAndPhoto(String userId, MultipartFile pic, UpdateUserRequest request) {
        User user = userRepository.findByUserId(userId).orElseThrow(IllegalArgumentException::new);
        if (pic != null) {
            String imageFileName = imageService.save(pic, false);
            user.setImgPath(imageFileName);
        }
        user.setNickName(request.getNickname());
        User updatedUser = userRepository.save(user);

        return new UserResponse(updatedUser);
    }

    public UserResponse withdrawal(String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(IllegalArgumentException::new);
        user.setUserId("");
        user.setPhone("");
        User updatedUser = userRepository.save(user);

        return new UserResponse(updatedUser);
    }

    public void updateCertification(String userId, UpdateUserCertifiRequest request) {
        User user = userRepository.findByUserId(userId).orElseThrow(IllegalArgumentException::new);
        user.setCertification(request.getIsCertification());
        userRepository.save(user);
    }


}