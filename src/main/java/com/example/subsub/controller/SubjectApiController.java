package com.example.subsub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import com.example.subsub.domain.Subject;
import com.example.subsub.dto.request.AddSubjectRequest;
import com.example.subsub.dto.response.SubjectResponse;
import com.example.subsub.service.SubjectService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "메인페이지", description = "메인페이지- 과목추가 API")
@RestController
@RequestMapping("/subject")
@RequiredArgsConstructor
public class SubjectApiController {

    private final SubjectService subjectService;

    @Operation(summary = "과목 정보입력하기", description = "과목과 관련된 정보를 입력하면 올라갑니다. ", tags = {"SubjectAPIController"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "입력성공",
                    content = @Content(schema = @Schema(implementation = SubjectApiController.class))),
            @ApiResponse(responseCode = "400", description = "확인되지 않은 접근입니다."),
            @ApiResponse(responseCode = "404", description = "찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생했습니다.")
    })


    // 생성
    @PostMapping
    public SubjectResponse save(@RequestBody AddSubjectRequest request, Authentication authentication) {
        System.out.println(authentication.getName());
        Subject savedSubject = subjectService.save(request, authentication.getName());
        return new SubjectResponse(savedSubject);
    }

    /*
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Subject subjectWriteForm(@RequestPart SubjectDTO subjectDTO, @RequestPart MultipartFile multipartFile, @RequestPart User user) throws Exception {
        subjectService.saveUser(user);
        return subjectService.write(subjectDTO, multipartFile,user);
    }

    @Operation(summary = "과목 정보 보여지기", description = "입력된 과목 정보를 확인합니다", tags = {"Subject Controller"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "입력성공",
                    content = @Content(schema = @Schema(implementation = SubjectApiController.class))),
            @ApiResponse(responseCode = "400", description = "확인되지 않은 접근입니다."),
            @ApiResponse(responseCode = "404", description = "찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생했습니다.")
    })
    */
    // 조회
    @GetMapping("/{id}")
    public ResponseEntity<SubjectResponse> findById(@PathVariable Integer id) {
        Subject subject = subjectService.findById(id);
        return ResponseEntity.ok().body(new SubjectResponse(subject));
    }
    /*
    @GetMapping("/{id}")
    public Optional<Subject> get(@PathVariable Integer id) {
        return subjectService.postView(id);
    }
    */

    // 모두 조회
    @GetMapping("/all")
    public ResponseEntity<List<Subject>> getAllSubject(Authentication authentication) throws Exception {
        List<Subject> savedSubject = subjectService.getAllSubject(authentication.getName());
        return new ResponseEntity<>( savedSubject, HttpStatus.OK);
    }
    /*
    @GetMapping("/{id}")
    public Optional<Subject> get(@PathVariable Integer id) {return subjectService.postView(id);
    }
    */


    @Operation(summary = "과목 정보 삭제", description = "입력된 과목 정보가 삭제됩니다.", tags = {"Subject Controller"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "입력성공",
                    content = @Content(schema = @Schema(implementation = SubjectApiController.class))),
            @ApiResponse(responseCode = "400", description = "확인되지 않은 접근입니다."),
            @ApiResponse(responseCode = "404", description = "찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생했습니다.")
    })

    @DeleteMapping("/delete/{id}")
    public boolean subjectDelete(@PathVariable Integer id) {
        subjectService.subjectDelete(id);
        return true;
    }
}
