package eggis0.baram.domain.certification.application;

import eggis0.baram.domain.certification.domain.Certification;
import eggis0.baram.domain.certification.dto.req.CertifiRequest;
import eggis0.baram.domain.certification.dto.res.CertifiResponse;
import eggis0.baram.domain.certification.exception.CertifiNotFoundException;
import eggis0.baram.domain.certification.exception.FailImageSaveException;
import eggis0.baram.domain.certification.exception.NullImageException;
import eggis0.baram.domain.certification.repository.CertificationRepository;
import eggis0.baram.domain.user.domain.User;
import eggis0.baram.domain.user.exception.UserNotFountException;
import eggis0.baram.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
@Transactional
@RequiredArgsConstructor
public class CertificationService {

    @Value("${image.path}")
    private String IMAGE_PATH;
    private final UserRepository userRepository;
    private final CertificationRepository certificationRepository;

    public CertifiResponse save(CertifiRequest request, MultipartFile pic, String userId) throws Exception {
        if (!userRepository.existsUserByUserId(userId)) {
            throw new UserNotFountException();
        }
        if (pic == null) {
            throw new NullImageException();
        }

        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + pic.getOriginalFilename();
        Path imagePath = Paths.get(IMAGE_PATH + imageFileName);
        try {
            Files.write(imagePath, pic.getBytes());
        } catch (Exception e) {
            throw new FailImageSaveException();
        }

        User user = userRepository.findByUserId(userId).get();
        Certification certification = Certification.builder()
                .user(user)
                .name(request.getName())
                .studentIdNumber(request.getStudentIdNumber())
                .requestAt(LocalDateTime.now())
                .imgPath(imageFileName)
                .build();

        certificationRepository.save(certification);
        return new CertifiResponse(certification);
    }

    public CertifiResponse get(Long id) {
        if (certificationRepository.existsById(id)) {
            throw new CertifiNotFoundException();
        }
        Certification certification = certificationRepository.findById(id).get();
        return new CertifiResponse(certification);
    }

    public List<CertifiResponse> getAll() {
        List<CertifiResponse> certifisDTO = new ArrayList<>();
        List<Certification> certifications = certificationRepository.findAllByOrderByUserIsCertification();

        for (Certification certification : certifications) {
            CertifiResponse dto = new CertifiResponse();
            dto.setCertiId(certification.getCertiId());
            dto.setName(certification.getName());
            dto.setStudentIdNumber(certification.getStudentIdNumber());
            dto.setUser(certification.getUser());
            dto.setImgPath(certification.getImgPath());
            certifisDTO.add(dto);
        }
        return certifisDTO;
    }
}