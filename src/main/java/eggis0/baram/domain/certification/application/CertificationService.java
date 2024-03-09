package eggis0.baram.domain.certification.application;

import eggis0.baram.domain.certification.dto.req.CertifiRequest;
import eggis0.baram.domain.certification.domain.Certification;
import eggis0.baram.domain.certification.repository.CertificationRepository;
import eggis0.baram.domain.user.domain.User;
import eggis0.baram.domain.user.repository.UserRepository;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CertificationService {

    @Value("${image.path}")
    private String IMAGE_PATH;

    private final UserRepository userRepository;

    private final CertificationRepository certificationRepository;

    public ResponseEntity<Certification> request(CertifiRequest request, MultipartFile pic, String userId) throws Exception {
        try {
            System.out.println("hello");
            String imageFileName = "default.png";

            if(pic!=null){
                UUID uuid = UUID.randomUUID();
                imageFileName = uuid+"_"+pic.getOriginalFilename();
                Path imagePath = Paths.get(IMAGE_PATH+imageFileName);
                try{
                    Files.write(imagePath,pic.getBytes());
                }catch (Exception e){

                }
            }else{
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            if (!userRepository.existsUserByUserId(userId)){
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            LocalDateTime requestAt = LocalDateTime.now();
            User user = userRepository.findByUserId(userId).get();
            Certification certification = Certification.builder()
                    .user(user)
                    .name(request.getName())
                    .studentIdNumber(request.getStudentIdNumber())
                    .requestAt(requestAt)
                    .imgPath(imageFileName)
                    .build();

            certificationRepository.save(certification);
            return new ResponseEntity<>(certification, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<List<Certification>> requests() throws Exception {
        List<Certification> certifications = certificationRepository.findAllByOrderByUserIsCertification();
        return new ResponseEntity<>(certifications, HttpStatus.OK);
    }

    public ResponseEntity<Certification> getRequest(Long id) throws Exception {
        Certification certification = certificationRepository.findById(id).get();
        return new ResponseEntity<>(certification, HttpStatus.OK);
    }
}