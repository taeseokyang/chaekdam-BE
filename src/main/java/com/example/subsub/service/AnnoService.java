package com.example.subsub.service;

import com.example.subsub.domain.Announcement;
import com.example.subsub.dto.request.AddAnnoRequest;
import com.example.subsub.dto.response.AnnoResponse;
import com.example.subsub.repository.AnnoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AnnoService {

    private final AnnoRepository annoRepository;

    // 생성
    public Announcement save(AddAnnoRequest request) {
        Announcement anno = Announcement.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .createdAt(request.getCreatedAt())
                .build();
        return annoRepository.save(anno);
    }

    // 모두 조회
    public List<AnnoResponse> getAllAnno() {
        List<AnnoResponse> annosDTO = new ArrayList<>();
        List<Announcement> annos = annoRepository.findAllByOrderByCreatedAtDesc();

        for(Announcement anno : annos){
            AnnoResponse dto = new AnnoResponse();
            dto.setAnnoId(anno.getAnnoId());
            dto.setTitle(anno.getTitle());
            dto.setContent(anno.getContent());
            dto.setCreatedAt(anno.getCreatedAt());
            annosDTO.add(dto);
        }
        return annosDTO;
    }

    public AnnoResponse getAnno(Integer annoId) {
        Announcement anno = annoRepository.findById(annoId).get();


        return new AnnoResponse(anno);
    }
    //삭제
    public ResponseEntity<String> deletePost(Integer annoId){
        if (annoRepository.existsById(annoId)) {
            annoRepository.deleteById(annoId);
            return ResponseEntity.status(HttpStatus.OK).body("Anno is deleted with ID:" + annoId);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Anno not found with ID: " + annoId);
    }
}