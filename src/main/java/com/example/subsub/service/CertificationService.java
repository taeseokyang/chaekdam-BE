package com.example.subsub.service;

import com.example.subsub.domain.Certification;
import com.example.subsub.domain.User;
import com.example.subsub.dto.request.CertifiRequest;
import com.example.subsub.repository.CertificationRepository;
import com.example.subsub.repository.UserRepository;
import com.example.subsub.utils.FilePath;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CertificationService {

    private final UserRepository userRepository;

    private final CertificationRepository certificationRepository;

    public ResponseEntity<Certification> request(CertifiRequest request, MultipartFile pic) throws Exception {
        try {

//            if (certificationRepository.existsUserByUserId(request.getUserid())){
//                return new ResponseEntity<>(new RegisterResponse(false, "중복된 ID"), HttpStatus.CONFLICT);
//            }
            String imageFileName = "default.png";

            if(pic!=null){
                UUID uuid = UUID.randomUUID();
                imageFileName = uuid+"_"+pic.getOriginalFilename();
                Path imagePath = Paths.get(FilePath.IMAGEPATH+imageFileName);
                try{
                    Files.write(imagePath,pic.getBytes());
                }catch (Exception e){

                }
            }else{
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            if (!userRepository.existsUserByUserId(request.getUserid())){
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            User user = userRepository.findByUserId(request.getUserid()).get();
            Certification certification = Certification.builder()
                    .user(user)
                    .requestAt(request.getRequestAt())
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
}