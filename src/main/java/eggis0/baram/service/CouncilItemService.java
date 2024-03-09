package eggis0.baram.service;

import eggis0.baram.domain.Council;
import eggis0.baram.domain.CouncilItem;
import eggis0.baram.domain.User;
import eggis0.baram.repository.CouncilItemRepository;
import eggis0.baram.repository.CouncilRepository;
import eggis0.baram.dto.request.AddCouncilItemRequest;
import eggis0.baram.dto.request.UpdateCouncilItemRequest;
import eggis0.baram.dto.response.CouncilItemResponse;
import eggis0.baram.repository.UserRepository;
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
public class CouncilItemService {

    private final CouncilItemRepository councilItemRepository;
    private final CouncilRepository councilRepository;
    private final UserRepository userRepository;

    // 생성
    public CouncilItem save(AddCouncilItemRequest request) {
        Council council = councilRepository.findById(request.getCouncilId()).get();
        CouncilItem councilItem = CouncilItem.builder()
                .council(council)
                .quantity(0)
                .name(request.getName())
                .type(request.getType())
                .build();
        return councilItemRepository.save(councilItem);
    }

    // 생성
    public CouncilItem CouncilSave(AddCouncilItemRequest request, String manager) {
        User user = userRepository.findByUserId(manager).get();
        Council council = councilRepository.findByManager(user);
        CouncilItem councilItem = CouncilItem.builder()
                .council(council)
                .quantity(0)
                .name(request.getName())
                .type(request.getType())
                .build();
        return councilItemRepository.save(councilItem);
    }


    // 모두 조회
    public List<CouncilItemResponse> getAllCouncilItem() {
        List<CouncilItemResponse> councilItemsDTO = new ArrayList<>();
        List<CouncilItem> councilItems = councilItemRepository.findAll();
        for(CouncilItem councilItem : councilItems){
            CouncilItemResponse dto = new CouncilItemResponse();
            dto.setCouncilId(councilItem.getCouncil().getCouncilId());
            dto.setName(councilItem.getName());
            dto.setQuantity(councilItem.getQuantity());
            dto.setType(councilItem.getType());
            councilItemsDTO.add(dto);
        }
        return councilItemsDTO;
    }

    //삭제
    public ResponseEntity<String> deleteCouncilItem(Integer councilItemId){
        if (councilItemRepository.existsById(councilItemId)) {
            councilItemRepository.deleteByItemId(councilItemId);
            return ResponseEntity.status(HttpStatus.OK).body("CouncilItem is deleted with ID:" + councilItemId);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CouncilItem not found with ID: " + councilItemId);
    }

    public ResponseEntity<CouncilItemResponse> update(Integer id, UpdateCouncilItemRequest request) {
        CouncilItem councilItem = councilItemRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        councilItem.setQuantity(request.getQuantity());
        CouncilItem updatedCouncilItem = councilItemRepository.save(councilItem);
        return ResponseEntity.ok(new CouncilItemResponse(updatedCouncilItem));
    }
}