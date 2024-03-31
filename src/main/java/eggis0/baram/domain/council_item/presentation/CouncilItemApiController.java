package eggis0.baram.domain.council_item.presentation;

import eggis0.baram.domain.council_item.application.CouncilItemService;
import eggis0.baram.domain.council_item.dto.req.AddCouncilItemRequest;
import eggis0.baram.domain.council_item.dto.req.UpdateCouncilItemRequest;
import eggis0.baram.domain.council_item.dto.res.CouncilItemResponse;
import eggis0.baram.domain.council_item.dto.res.SearchResponse;
import eggis0.baram.global.config.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static eggis0.baram.domain.council_item.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/council-item") // /admin/council-item
@RequiredArgsConstructor
public class CouncilItemApiController {

    private final CouncilItemService councilItemService;

    @PostMapping
    public ResponseDto<CouncilItemResponse> save(@RequestBody AddCouncilItemRequest request) {
        CouncilItemResponse response = councilItemService.save(request);
        return ResponseDto.of(OK.value(), SUCCESS_CREATE.getMessage(), response);
    }

    @GetMapping("/search/{keyword}")
    public ResponseDto<List<SearchResponse>> getByKeyword(@PathVariable String keyword) {
        List<SearchResponse> responses = councilItemService.getByKeyword(keyword);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), responses);
    }

    @GetMapping("/all")
    public ResponseDto<List<CouncilItemResponse>> getAll() {
        List<CouncilItemResponse> responses = councilItemService.getAll();
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), responses);
    }

    @DeleteMapping("/{id}")
    public ResponseDto delete(@PathVariable Integer id) {
        councilItemService.delete(id);
        return ResponseDto.of(OK.value(), SUCCESS_DELETE.getMessage());
    }

    @PutMapping("/{id}")
    public ResponseDto update(@PathVariable Integer id, @RequestBody UpdateCouncilItemRequest request) {
        councilItemService.update(id, request);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }
}
