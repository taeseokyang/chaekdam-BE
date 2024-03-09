package eggis0.baram.controller;

import eggis0.baram.domain.Council;
import eggis0.baram.dto.request.AddCouncilRequest;
import eggis0.baram.dto.request.UpdateCouncilRequest;
import eggis0.baram.dto.response.CouncilResponse;
import eggis0.baram.service.CouncilService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/manage/council")
@RequiredArgsConstructor
public class CouncilManageApiController {

    private final CouncilService councilService;

    // 생성
    @PostMapping
    public CouncilResponse save(@RequestPart AddCouncilRequest request, @RequestPart(required = false) MultipartFile pic) throws Exception {
        Council council = councilService.save(request, pic);
        return new CouncilResponse(council);
    }

    // 조회
    @GetMapping
    public ResponseEntity<CouncilResponse> findByManager(Authentication authentication) {
        Council council = councilService.getCouncilByManager(authentication.getName());
        return ResponseEntity.ok().body(new CouncilResponse(council, council.getManager().getImgPath()));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCouncil(@PathVariable Integer id) {
        ResponseEntity<String> result = councilService.deleteCouncil(id);
        return result;
    }

    @PutMapping("/{id}")
    public ResponseEntity<CouncilResponse>  updateCouncil(@PathVariable Integer id, @RequestBody UpdateCouncilRequest request) {
        ResponseEntity<CouncilResponse> council = councilService.update(id, request);
        return council;
    }

    @PutMapping("/{id}/{isCouncilSelfManage}")
    public void  changeManager(@PathVariable Integer id, @PathVariable Boolean isCouncilSelfManage) {
        councilService.changeManager(id, isCouncilSelfManage);
    }
}
