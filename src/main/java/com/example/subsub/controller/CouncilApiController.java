package com.example.subsub.controller;

import com.example.subsub.domain.Council;
import com.example.subsub.dto.request.AddCouncilRequest;
import com.example.subsub.dto.request.UpdateCouncilRequest;
import com.example.subsub.dto.response.CouncilResponse;
import com.example.subsub.dto.response.CouncilsResponse;
import com.example.subsub.service.CouncilService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/council")
@RequiredArgsConstructor
public class CouncilApiController {

    private final CouncilService councilService;

    // 생성
    @PostMapping
    public CouncilResponse save(@RequestBody AddCouncilRequest request) {
        Council council = councilService.save(request);
        return new CouncilResponse(council);
    }

    // 조회
    @GetMapping("/{id}")
    public ResponseEntity<CouncilResponse> findById(@PathVariable Integer id) {
        Council council = councilService.getCouncil(id);
        return ResponseEntity.ok().body(new CouncilResponse(council));
    }


    @GetMapping("/all")
    public ResponseEntity<List<CouncilsResponse>> getAllCouncil() throws Exception {
        List<CouncilsResponse> councils = councilService.getAllCouncil();
        return ResponseEntity.ok(councils);
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
}
