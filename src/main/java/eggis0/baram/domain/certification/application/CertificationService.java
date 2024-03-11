package eggis0.baram.domain.certification.application;

import eggis0.baram.domain.certification.domain.Certification;
import eggis0.baram.domain.certification.dto.req.CertifiRequest;
import eggis0.baram.domain.certification.dto.res.CertifiResponse;
import eggis0.baram.domain.certification.exception.CertifiNotFoundException;
import eggis0.baram.domain.certification.repository.CertificationRepository;
import eggis0.baram.domain.image.application.ImageService;
import eggis0.baram.domain.user.domain.User;
import eggis0.baram.domain.user.exception.UserNotFountException;
import eggis0.baram.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CertificationService {

    private final UserRepository userRepository;
    private final CertificationRepository certificationRepository;
    private final ImageService imageService;

    public CertifiResponse save(CertifiRequest request, MultipartFile pic, String userId) throws Exception {
        if (!userRepository.existsUserByUserId(userId)) {
            throw new UserNotFountException();
        }

        String imageFileName = imageService.save(pic);
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
        if (!certificationRepository.existsById(id)) {
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
            dto.setRequestAt(certification.getRequestAt());
            dto.setImgPath(certification.getImgPath());
            certifisDTO.add(dto);
        }
        return certifisDTO;
    }
}