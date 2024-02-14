package com.example.subsub.controller;

import com.example.subsub.domain.Council;
import com.example.subsub.dto.request.AddCouncilRequest;
import com.example.subsub.dto.request.UpdateCouncilRequest;
import com.example.subsub.dto.response.CouncilResponse;
import com.example.subsub.dto.response.CouncilsResponse;
import com.example.subsub.service.CouncilService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
