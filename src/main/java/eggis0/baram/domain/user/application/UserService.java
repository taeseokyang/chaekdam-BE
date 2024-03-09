package eggis0.baram.domain.user.application;

import eggis0.baram.domain.oauth.application.OAuthService;
import eggis0.baram.domain.user.domain.Role;
import eggis0.baram.domain.user.domain.User;
import eggis0.baram.domain.user.dto.req.UpdateUserRequest;
import eggis0.baram.domain.user.dto.req.UserRequest;
import eggis0.baram.domain.user.dto.res.RegisterResponse;
import eggis0.baram.domain.user.dto.res.UserResponse;
import eggis0.baram.domain.user.repository.UserRepository;
import eggis0.baram.global.config.security.JwtProvider;
import eggis0.baram.domain.certification.dto.req.UpdateUserCertifiRequest;
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
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    @Value("${image.path}")
    private String IMAGE_PATH;

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final OAuthService oAuthService;

    public ResponseEntity<UserResponse> kakaoLogin(String code) throws Exception {
        String token = oAuthService.getKakaoAccessToken(code);
        String email = oAuthService.getEmail(token);

        if (userRepository.existsUserByUserId(email)){
            User user = userRepository.findByUserId(email).get();
            UserResponse signResponse= UserResponse.builder()
                    .id(user.getId())
                    .userId(user.getUserId())
                    .nickname(user.getNickName())
                    .roles(user.getRole())
                    .token(jwtProvider.createToken(user.getUserId(), user.getRole()))
                    .message("로그인 성공")
                    .build();
            return new ResponseEntity<>(signResponse, HttpStatus.OK);

        }
        UserResponse signResponse=  UserResponse.builder()
                .userId(email)
                .message("계정이 존재하지 않아 회원가입이 필요합니다")
                .build();
        return new ResponseEntity<>(signResponse, HttpStatus.CREATED);
    }

    public ResponseEntity<UserResponse> kakaoRegister(String nickname, String email) throws Exception {
        try {
            if (userRepository.existsUserByUserId(email)){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            String imageFileName = "default.png";
            String id = UUID.randomUUID().toString();
            User user = User.builder()
                    .id(id)
                    .userId(email)
                    .nickName(nickname)
                    .imgPath(imageFileName)
                    .borrowCount(0)
                    .lendCount(0)
                    .role(Role.USER)
                    .build();
            userRepository.save(user);
            UserResponse signResponse= UserResponse.builder()
                    .id(user.getId())
                    .userId(user.getUserId())
                    .nickname(user.getNickName())
                    .roles(user.getRole())
                    .token(jwtProvider.createToken(user.getUserId(), user.getRole()))
                    .message("로그인 성공")
                    .build();
            return new ResponseEntity<>(signResponse, HttpStatus.OK);


        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }


    public ResponseEntity<UserResponse> login(UserRequest request) throws Exception {
        if (userRepository.existsUserByUserId(request.getUserid())){
            User user = userRepository.findByUserId(request.getUserid()).get();

//            if (!passwordEncoder.matches(request.getPassword(), user.getPassWord())) {
//                UserResponse signResponse = UserResponse.builder()
//                        .message("비밀 번호가 틀립니다.")
//                        .build();
//                return new ResponseEntity<>(signResponse, HttpStatus.UNAUTHORIZED);
//            }

            UserResponse signResponse= UserResponse.builder()
                    .id(user.getId())
                    .userId(user.getUserId())
                    .nickname(user.getNickName())
                    .roles(user.getRole())
                    .token(jwtProvider.createToken(user.getUserId(), user.getRole()))
                    .message("로그인 성공")
                    .build();
            return new ResponseEntity<>(signResponse, HttpStatus.OK);

        }
        UserResponse signResponse=  UserResponse.builder()
                .message("계정이 존재하지 않습니다.")
                .build();
        return new ResponseEntity<>(signResponse, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<RegisterResponse> register(UserRequest request, MultipartFile pic) throws Exception {
        try {

            if (userRepository.existsUserByUserId(request.getUserid())){
                return new ResponseEntity<>(new RegisterResponse(false, "중복된 ID"), HttpStatus.CONFLICT);
            }
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
        return new ResponseEntity<>(new RegisterResponse(true, "회원가입 성공"), HttpStatus.OK);
    }

    public ResponseEntity<UserResponse> getUser(String id) throws Exception {
        if (userRepository.existsUserById(id)){
            User user = userRepository.findById(id).get();
            return new ResponseEntity<>(new UserResponse(user, "계정 조회 성공"), HttpStatus.OK);
        }else{
            UserResponse signResponse = UserResponse.builder()
                    .message("계정이 존재하지 않습니다.")
                    .build();
            return new ResponseEntity<>(signResponse, HttpStatus.NOT_FOUND);
        }

    }

    public ResponseEntity<UserResponse> updateNicknameAndPhoto(String userId, MultipartFile pic, UpdateUserRequest request) {
        User user = userRepository.findByUserId(userId).orElseThrow(IllegalArgumentException::new);


        if(pic!=null){
            String imageFileName;
            UUID uuid = UUID.randomUUID();
            imageFileName = uuid+"_"+pic.getOriginalFilename();
            Path imagePath = Paths.get(IMAGE_PATH+imageFileName);
            try{
                Files.write(imagePath,pic.getBytes());
            }catch (Exception e){

            }
            user.setImgPath(imageFileName);
        }
        user.setNickName(request.getNickname());
        User updatedUser = userRepository.save(user);

        return ResponseEntity.ok(new UserResponse(updatedUser, "변경 성공"));
    }

    public ResponseEntity<UserResponse> updateCertification(String userId, UpdateUserCertifiRequest request) {
        User user = userRepository.findByUserId(userId).orElseThrow(IllegalArgumentException::new);
        user.setCertification(request.getIsCertification());
        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(new UserResponse(updatedUser, "변경 성공"));
    }


}