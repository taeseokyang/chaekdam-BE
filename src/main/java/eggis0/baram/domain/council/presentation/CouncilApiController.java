package eggis0.baram.domain.council.presentation;

import eggis0.baram.domain.council.application.CouncilService;
import eggis0.baram.domain.council.dto.res.CouncilResponse;
import eggis0.baram.domain.council.dto.res.CouncilsResponse;
import eggis0.baram.global.config.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static eggis0.baram.domain.council.presentation.constant.ResponseMessage.SUCCESS_READ;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/council") // councils
@RequiredArgsConstructor
public class CouncilApiController {

    private final CouncilService councilService;

    @GetMapping("/{id}")
    public ResponseDto<CouncilResponse> get(@PathVariable Integer id) {
        CouncilResponse response = councilService.get(id);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }

    @GetMapping("/all") //
    public ResponseDto<List<CouncilsResponse>> getAll(@RequestParam(name = "campus", required = false) String campus) {
        if (campus == null) {
            List<CouncilsResponse> responses = councilService.getAll();
            return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), responses);
        }
        List<CouncilsResponse> responses = councilService.getAllByCampus(campus);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), responses);
    }

}
