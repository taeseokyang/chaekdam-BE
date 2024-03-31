package eggis0.baram.domain.council_item.application;

import eggis0.baram.domain.council.domain.Council;
import eggis0.baram.domain.council.exception.CouncilNotFoundException;
import eggis0.baram.domain.council.repository.CouncilRepository;
import eggis0.baram.domain.council_item.domain.CouncilItem;
import eggis0.baram.domain.council_item.dto.req.AddCouncilItemRequest;
import eggis0.baram.domain.council_item.dto.req.UpdateCouncilItemRequest;
import eggis0.baram.domain.council_item.dto.res.CouncilItemResponse;
import eggis0.baram.domain.council_item.dto.res.SearchResponse;
import eggis0.baram.domain.council_item.exception.CouncilItemNotFoundException;
import eggis0.baram.domain.council_item.repository.CouncilItemRepository;
import eggis0.baram.domain.user.domain.User;
import eggis0.baram.domain.user.exception.UserNotFountException;
import eggis0.baram.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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

    public CouncilItemResponse save(AddCouncilItemRequest request) {
        Council council = councilRepository.findById(request.getCouncilId()).get();
        CouncilItem councilItem = CouncilItem.builder()
                .council(council)
                .quantity(0)
                .name(request.getName())
                .type(request.getType())
                .build();
        CouncilItem savedCouncilItem = councilItemRepository.save(councilItem);
        return new CouncilItemResponse(savedCouncilItem);
    }

    public CouncilItemResponse saveByCouncil(AddCouncilItemRequest request, String manager) {
        if (!userRepository.existsUserByUserId(manager)) {
            throw new UserNotFountException();
        }
        User user = userRepository.findByUserId(manager).get();

        if (!councilRepository.existsByManager(user)) {
            throw new CouncilNotFoundException();
        }
        Council council = councilRepository.findByManager(user);

        CouncilItem councilItem = CouncilItem.builder()
                .council(council)
                .quantity(0)
                .name(request.getName())
                .type(request.getType())
                .build();
        CouncilItem savedCouncilItem = councilItemRepository.save(councilItem);
        return new CouncilItemResponse(savedCouncilItem);
    }

    public List<CouncilItemResponse> getAll() {
        List<CouncilItemResponse> councilItemsDTO = new ArrayList<>();
        List<CouncilItem> councilItems = councilItemRepository.findAll();
        for (CouncilItem councilItem : councilItems) {
            CouncilItemResponse dto = new CouncilItemResponse();
            dto.setCouncilId(councilItem.getCouncil().getCouncilId());
            dto.setName(councilItem.getName());
            dto.setQuantity(councilItem.getQuantity());
            dto.setType(councilItem.getType());
            councilItemsDTO.add(dto);
        }
        return councilItemsDTO;
    }

    public List<SearchResponse> getByKeyword(String keyword) {
        List<SearchResponse> councilItemsDTO = new ArrayList<>();
        List<CouncilItem> councilItems = councilItemRepository.findTop5ByNameContaining(keyword);
        for (CouncilItem councilItem : councilItems) {
            SearchResponse dto = new SearchResponse(councilItem);
            councilItemsDTO.add(dto);
        }
        return councilItemsDTO;
    }


    public void delete(Integer councilItemId) {
        if (!councilItemRepository.existsById(councilItemId)) {
            throw new CouncilItemNotFoundException();
        }
        councilItemRepository.deleteByItemId(councilItemId);
    }

    public void update(Integer councilItemId, UpdateCouncilItemRequest request) {
        if (!councilItemRepository.existsById(councilItemId)) {
            throw new CouncilItemNotFoundException();
        }
        CouncilItem councilItem = councilItemRepository.findById(councilItemId).get();
        councilItem.setQuantity(request.getQuantity());
        councilItemRepository.save(councilItem);
    }
}