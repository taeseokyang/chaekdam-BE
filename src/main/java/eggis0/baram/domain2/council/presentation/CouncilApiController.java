package eggis0.baram.domain2.council.presentation;

import eggis0.baram.domain2.council.dto.res.CouncilResponse;
import eggis0.baram.domain2.council.domain.CouncilService;
import eggis0.baram.domain2.council.dto.res.CouncilsResponse;
import eggis0.baram.domain2.council.domain.Council;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/council")
@RequiredArgsConstructor
public class CouncilApiController {

    private final CouncilService councilService;

    // 조회
    @GetMapping("/{id}")
    public ResponseEntity<CouncilResponse> findById(@PathVariable Integer id) {
        Council council = councilService.getCouncil(id);
        return ResponseEntity.ok().body(new CouncilResponse(council, council.getManager().getImgPath()));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CouncilsResponse>> getCouncils(@RequestParam(name = "campus", required = false) String campus) throws Exception {
        if (campus == null){
            List<CouncilsResponse> councils = councilService.getAllCouncil();
            return ResponseEntity.ok(councils);
        }else{
            List<CouncilsResponse> councils = councilService.getCouncilsByCampus(campus);
            return ResponseEntity.ok(councils);
        }
    }

}
