package eggis0.baram.domain2.announcement.application;

import eggis0.baram.domain2.announcement.dto.req.AddAnnoRequest;
import eggis0.baram.domain2.announcement.dto.res.AnnoResponse;
import eggis0.baram.domain2.announcement.domain.Announcement;
import eggis0.baram.domain2.announcement.repository.AnnoRepository;
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