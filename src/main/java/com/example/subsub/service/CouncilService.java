package com.example.subsub.service;

import com.example.subsub.domain.Council;
import com.example.subsub.domain.ItemType;
import com.example.subsub.domain.User;
import com.example.subsub.dto.response.CouncilResponse;
import com.example.subsub.dto.response.CouncilsResponse;
import com.example.subsub.repository.CouncilItemRepository;
import com.example.subsub.repository.CouncilRepository;
import com.example.subsub.dto.request.AddCouncilRequest;
import com.example.subsub.dto.request.UpdateCouncilRequest;
import com.example.subsub.repository.UserRepository;
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
public class CouncilService {

    private final CouncilRepository councilRepository;
    private final CouncilItemRepository councilItemRepository;
    private final UserRepository userRepository;

    // 생성
    public Council save(AddCouncilRequest request, String manager) {
        User user = userRepository.findByUserId(manager).get();
        Council council = Council.builder()
                .name(request.getName())
                .manager(user)
                .college(request.getCollege())
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

    public Council getCouncilByManager(String manager) {
        User user = userRepository.findByUserId(manager).get();
        return councilRepository.findByManager(user);
    }

    // 모두 조회
    public List<CouncilsResponse> getAllCouncil() {
        List<CouncilsResponse> councilsDTO = new ArrayList<>();
        List<Council> councils = councilRepository.findAllByOrderByCollege();
        for(Council council : councils){
            CouncilsResponse dto = new CouncilsResponse();
            dto.setCouncilId(council.getCouncilId());
            dto.setCollege(council.getCollege());
            dto.setName(council.getName());
            dto.setProvidedItemCount(councilItemRepository.countByCouncilAndType(council, ItemType.PROVIDED));
            dto.setRentalItemCount(councilItemRepository.countByCouncilAndType(council, ItemType.RENTAL));
            councilsDTO.add(dto);
        }
        return councilsDTO;
    }

    //삭제
    public ResponseEntity<String> deleteCouncil(Integer councilId){
        if (councilRepository.existsById(councilId)) {
            councilRepository.deleteByCouncilId(councilId);
            return ResponseEntity.status(HttpStatus.OK).body("Council is deleted with ID:" + councilId);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Council not found with ID: " + councilId);
    }


    public ResponseEntity<CouncilResponse> update(Integer id, UpdateCouncilRequest request) {
        Council council = councilRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        council.setLocation(request.getLocation());
        council.setOperatingHours(request.getOperatingHours());
        council.setUsageGuidelines(request.getUsageGuidelines());
        Council updatedCouncil = councilRepository.save(council);
        return ResponseEntity.ok(new CouncilResponse(updatedCouncil));
    }
}