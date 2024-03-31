package eggis0.baram.domain.council.application;

import eggis0.baram.domain.council.domain.Council;
import eggis0.baram.domain.council.dto.req.AddCouncilRequest;
import eggis0.baram.domain.council.dto.req.UpdateCouncilRequest;
import eggis0.baram.domain.council.dto.res.CouncilResponse;
import eggis0.baram.domain.council.dto.res.CouncilsResponse;
import eggis0.baram.domain.council.exception.CouncilNotFoundException;
import eggis0.baram.domain.council.repository.CouncilRepository;
import eggis0.baram.domain.council_item.domain.ItemType;
import eggis0.baram.domain.council_item.repository.CouncilItemRepository;
import eggis0.baram.domain.user.application.UserService;
import eggis0.baram.domain.user.domain.Role;
import eggis0.baram.domain.user.domain.User;
import eggis0.baram.domain.user.dto.req.UserRequest;
import eggis0.baram.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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

    private static final String INIT_COUNCIL_ID = "council";
    private static final String INIT_COUNCIL_PW = "0000";
    private static final String GLOBAL = "global";
    private static final String PREFIX_GLOBAL = "G";
    private static final String PREFIX_MEDICAL = "M";

    public CouncilResponse save(AddCouncilRequest request, MultipartFile pic) throws Exception {
        Council council = Council.builder()
                .name(request.getName())
                .college(request.getCollege())
                .location(request.getLocation())
                .operatingHours(request.getOperatingHours())
                .usageGuidelines(request.getUsageGuidelines())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .isCouncilSelfManage(false)
                .build();

        Council savedCouncil = councilRepository.save(council);
        UserRequest userRequest = new UserRequest();
        userRequest.setNickname(request.getName());
        userRequest.setUserid(INIT_COUNCIL_ID + savedCouncil.getCouncilId());
        userRequest.setPassword(INIT_COUNCIL_PW);
        userRequest.setRole(Role.MANAGER);
        userService.register(userRequest, pic);
        User user = userRepository.findByUserId(INIT_COUNCIL_ID + savedCouncil.getCouncilId()).get();
        savedCouncil.setManager(user);
        savedCouncil = councilRepository.save(savedCouncil);
        return new CouncilResponse(savedCouncil);
    }

    public CouncilResponse get(Integer id) {
        if (!councilRepository.existsById(id)) {
            throw new CouncilNotFoundException();
        }
        Council council = councilRepository.findById(id).get();
        return new CouncilResponse(council, council.getManager().getImgPath());
    }

    public CouncilResponse getCouncilByManager(String manager) {
        User user = userRepository.findByUserId(manager).get();
        Council council = councilRepository.findByManager(user);
        return new CouncilResponse(council, council.getManager().getImgPath());
    }

    // 모두 조회
    public List<CouncilsResponse> getAll() {
        List<CouncilsResponse> councilsDTO = new ArrayList<>();
        List<Council> councils = councilRepository.findAllByOrderByCollege();
        for (Council council : councils) {
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
    public List<CouncilsResponse> getAllByCampus(String campus) {
        List<CouncilsResponse> councilsDTO = new ArrayList<>();
        List<Council> councils = councilRepository.findAllByCollegeStartingWithOrderByCollege(campus.equals(GLOBAL) ? PREFIX_GLOBAL : PREFIX_MEDICAL);
        for (Council council : councils) {
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

    public void delete(Integer councilId) {
        if (!councilRepository.existsById(councilId)) {
            throw new CouncilNotFoundException();
        }
        councilRepository.deleteByCouncilId(councilId);
    }


    public void update(Integer councilId, UpdateCouncilRequest request) {
        if (!councilRepository.existsById(councilId)) {
            throw new CouncilNotFoundException();
        }
        Council council = councilRepository.findById(councilId).get();
        council.setLocation(request.getLocation());
        council.setOperatingHours(request.getOperatingHours());
        council.setUsageGuidelines(request.getUsageGuidelines());
        councilRepository.save(council);
    }

    public void changeManager(Integer councilId, Boolean isCouncilSelfManage) {
        if (!councilRepository.existsById(councilId)) {
            throw new CouncilNotFoundException();
        }
        Council council = councilRepository.findById(councilId).get();
        council.setIsCouncilSelfManage(isCouncilSelfManage);
    }
}