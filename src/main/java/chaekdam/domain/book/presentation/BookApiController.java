package chaekdam.domain.book.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import chaekdam.domain.book.application.BookService;
import chaekdam.domain.book.dto.res.BookSearchResponse;
import chaekdam.domain.book.presentation.constant.ResponseMessage;
import chaekdam.global.config.dto.ResponseDto;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookApiController {

    private final BookService bookService;

    // 검색 api
    @GetMapping("/search")
    public ResponseDto<List<BookSearchResponse>> search(@RequestParam String keyword) {
        List<BookSearchResponse> responses = bookService.searchFromAladin(keyword);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), responses);
    }
}
