package com.example.subsub.service;

import com.example.subsub.domain.Council;
import com.example.subsub.domain.Post;
import com.example.subsub.domain.User;
import com.example.subsub.dto.request.AddCouncilRequest;
import com.example.subsub.dto.request.AddPostRequest;
import com.example.subsub.dto.request.UpdateCouncilRequest;
import com.example.subsub.dto.request.UpdatePostRequest;
import com.example.subsub.dto.response.CouncilResponse;
import com.example.subsub.dto.response.PostResponse;
import com.example.subsub.dto.response.PostsResponse;
import com.example.subsub.repository.CouncilRepository;
import com.example.subsub.repository.PostRepository;
import com.example.subsub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CouncilService {

    private final CouncilRepository councilRepository;

    // 생성
    public Council save(AddCouncilRequest request) {
        Council council = Council.builder()
                .name(request.getName())
                .location(request.getLocation())
                .operatingHours(request.getOperatingHours())
                .usageGuidelines(request.getUsageGuidelines())
                .build();
        return councilRepository.save(council);
    }

    // 조회
    public Council getCouncil(Integer id) {
        return councilRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    // 모두 조회
    public List<CouncilResponse> getAllCouncil() {
        List<CouncilResponse> councilsDTO = new ArrayList<>();
        List<Council> councils = councilRepository.findAll();
        for(Council council : councils){
            CouncilResponse dto = new CouncilResponse();
            dto.setCouncilId(council.getCouncilId());
            dto.setName(council.getName());
            dto.setLocation(council.getLocation());
            dto.setOperatingHours(council.getOperatingHours());
            dto.setUsageGuidelines(council.getUsageGuidelines());
            councilsDTO.add(dto);
        }
        return councilsDTO;
    }

    //삭제
    public ResponseEntity<String> deleteCouncil(Integer councilId){
        if (councilRepository.existsById(councilId)) {
            councilRepository.deleteByCouncilId(councilId);
            return ResponseEntity.status(HttpStatus.OK).body("Post is deleted with ID:" + councilId);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found with ID: " + councilId);
    }

    public ResponseEntity<CouncilResponse> update(Integer id, UpdateCouncilRequest request) {
        Council council = councilRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        council.setName(request.getName());
        council.setLocation(request.getLocation());
        council.setOperatingHours(request.getOperatingHours());
        council.setUsageGuidelines(request.getUsageGuidelines());
        Council updatedCouncil = councilRepository.save(council);
        return ResponseEntity.ok(new CouncilResponse(updatedCouncil));
    }
}