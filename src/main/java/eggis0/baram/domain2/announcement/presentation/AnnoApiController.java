package eggis0.baram.domain2.announcement.presentation;

import eggis0.baram.domain2.announcement.dto.req.AddAnnoRequest;
import eggis0.baram.domain2.announcement.dto.res.AnnoResponse;
import eggis0.baram.domain2.announcement.domain.Announcement;
import eggis0.baram.domain2.announcement.application.AnnoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/anno")
@RequiredArgsConstructor
public class AnnoApiController {

    private final AnnoService annoService;

    // 생성
    @PostMapping
    public ResponseEntity<AnnoResponse> save(@RequestBody AddAnnoRequest request, Authentication authentication) {
        System.out.println(authentication.getAuthorities());
        Announcement anno = annoService.save(request);
        return ResponseEntity.ok().body(new AnnoResponse(anno));
    }

    // 모두 조회 by userid
    @GetMapping("/{id}")
    public ResponseEntity<AnnoResponse> getAnno(@PathVariable Integer id) throws Exception {
        AnnoResponse annos = annoService.getAnno(id);
        return ResponseEntity.ok(annos);
    }

    // 모두 조회 by userid
    @GetMapping("/all")
    public ResponseEntity<List<AnnoResponse>> getAllAnno() throws Exception {
        List<AnnoResponse> annos = annoService.getAllAnno();
        return ResponseEntity.ok(annos);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAnno(@PathVariable Integer id) {
        ResponseEntity<String> result = annoService.deletePost(id);
        return result;
    }
}
