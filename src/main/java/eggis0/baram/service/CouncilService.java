package eggis0.baram.service;

import eggis0.baram.domain.Council;
import eggis0.baram.domain.ItemType;
import eggis0.baram.domain.Role;
import eggis0.baram.domain.User;
import eggis0.baram.dto.request.UserRequest;
import eggis0.baram.dto.response.CouncilResponse;
import eggis0.baram.dto.response.CouncilsResponse;
import eggis0.baram.repository.CouncilItemRepository;
import eggis0.baram.repository.CouncilRepository;
import eggis0.baram.dto.request.AddCouncilRequest;
import eggis0.baram.dto.request.UpdateCouncilRequest;
import eggis0.baram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CouncilService {

    private final CouncilRepository councilRepository;
    private final CouncilItemRepository councilItemRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    // 생성
    public Council save(AddCouncilRequest request, MultipartFile pic) throws Exception {
        Council council = Council.builder()
                .name(request.getName())
                .college(request.getCollege())
                .location(request.getLocation())
                .operatingHours(request.getOperatingHours())
                .usageGuidelines(request.getUsageGuidelines())
                .isCouncilSelfManage(false)
                .build();
        Council savedCouncil = councilRepository.save(council);
        UserRequest userRequest = new UserRequest();
        userRequest.setNickname(request.getName());
        userRequest.setUserid("council"+savedCouncil.getCouncilId());
        userRequest.setPassword("0000");
        userRequest.setRole(Role.MANAGER);
        userService.register(userRequest, pic);
        User user = userRepository.findByUserId("council"+savedCouncil.getCouncilId()).get();
        savedCouncil.setManager(user);
        return councilRepository.save(savedCouncil);
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
            dto.setImgPath(council.getManager().getImgPath());
            councilsDTO.add(dto);
        }
        return councilsDTO;
    }

    // 모두 조회
    public List<CouncilsResponse> getCouncilsByCampus(String campus) {
        List<CouncilsResponse> councilsDTO = new ArrayList<>();
        List<Council> councils = councilRepository.findAllByCollegeStartingWithOrderByCollege(campus.equals("global") ? "G" : "M");
        for(Council council : councils){
            CouncilsResponse dto = new CouncilsResponse();
            dto.setCouncilId(council.getCouncilId());
            dto.setCollege(council.getCollege());
            dto.setName(council.getName());
            dto.setProvidedItemCount(councilItemRepository.countByCouncilAndType(council, ItemType.PROVIDED));
            dto.setRentalItemCount(councilItemRepository.countByCouncilAndType(council, ItemType.RENTAL));
            dto.setImgPath(council.getManager().getImgPath());
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

    public void changeManager(Integer id, Boolean isCouncilSelfManage) {
        Council council = councilRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        council.setIsCouncilSelfManage(isCouncilSelfManage);
    }
}