package chaekdam.domain.book.application;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import chaekdam.domain.book.domain.Book;
import chaekdam.domain.book.dto.req.AddBookRequest;
import chaekdam.domain.book.dto.res.BookDetailResponse;
import chaekdam.domain.book.dto.res.BookResponse;
import chaekdam.domain.book.dto.res.BookSearchResponse;
import chaekdam.domain.book.exception.BookNotFoundException;
import chaekdam.domain.book.repository.BookRepository;
import chaekdam.domain.image.application.ImageService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@EnableAsync
public class BookService {

    @Value("${aladin.ttb-key}")
    private String TTB_KEY;
    private final BookRepository bookRepository;
    private final ImageService imageService;
    public static final int PAGE_SIZE = 10;

    public Book save(AddBookRequest request) {
        Book book = Book.builder()
                .isbn(request.getIsbn())
                .title(request.getTitle())
                .coverImgName(request.getCoverImgName())
                .author(request.getAuthor())
                .publisher(request.getPublisher())
                .build();
        return bookRepository.save(book);
    }

    private String makeSearchUrl(String keyword){
        return "http://www.aladin.co.kr/ttb/api/ItemSearch.aspx?ttbkey="+TTB_KEY+"&Query="+keyword+"&QueryType=Title&MaxResults="+PAGE_SIZE+"&start=0&SearchTarget=Book&output=js&Version=20131101";
    }

    private String makeLookUpUrl(String isbn){
        return "https://www.aladin.co.kr/ttb/api/ItemLookUp.aspx?ttbkey="+TTB_KEY+"&itemIdType=ISBN&ItemId="+isbn+"&output=js&Version=20131101&OptResult=packing";
    }

    public List<BookSearchResponse> searchFromAladin(String keyword) {
        List<BookSearchResponse> responses = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());
        ResponseEntity<Map> resultMap = restTemplate.exchange(makeSearchUrl(keyword), HttpMethod.GET, entity, Map.class);
        Map<String, Object> body = resultMap.getBody();
        if (body == null) throw new BookNotFoundException();

        List<Map<String, Object>> items = (List<Map<String, Object>>) body.get("item");
        for (Map<String, Object> itemMap : items) {
            responses.add(makeSearchItemResponse(itemMap));
        }
        return responses;
    }

    public BookSearchResponse makeSearchItemResponse(Map<String, Object> itemMap) {
        String isbn = itemMap.get("isbn13").toString();
        String title = removeSubtitle(itemMap.get("title").toString());
        String bookCoverImgName = replaceCoverUrl(itemMap.get("cover").toString());
        String author = extractAuthors(itemMap.get("author").toString());
        String publisher = itemMap.get("publisher").toString();
        return new BookSearchResponse(isbn, title, bookCoverImgName, author, publisher);
    }

    public Book getBook(String isbn) {
        Optional<Book> optionalBook = bookRepository.findByIsbn(isbn);
        return optionalBook.orElseGet(() -> lookUpFromAladin(isbn));
    }

    public Book lookUpFromAladin(String isbn) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());
        ResponseEntity<Map> resultMap = restTemplate.exchange(makeLookUpUrl(isbn), HttpMethod.GET, entity, Map.class);
        Map<String, Object> body = resultMap.getBody();
        if (body == null) throw new BookNotFoundException();

        List<Map<String, Object>> items = (List<Map<String, Object>>) body.get("item");
        Map<String, Object> item = items.get(0);
        String title = item.get("title").toString();
        String bookCoverImgName = replaceCoverUrl(item.get("cover").toString());
        String author = extractAuthors(item.get("author").toString());
        String publisher = item.get("publisher").toString();
        Map<String, Object> subInfo = (Map<String, Object>) item.get("subInfo");
        String subTitle = subInfo.get("subTitle").toString();
        title = removeSubtitle2(title, subTitle);

        AddBookRequest request = AddBookRequest.builder()
                .isbn(isbn)
                .title(title)
                .author(author)
                .publisher(publisher)
                .coverImgName(bookCoverImgName)
                .build();
        return this.save(request);
    }

    public static String replaceCoverUrl(String original) {
        return original.replace("coversum", "cover500");
    }

    public static String removeSubtitle(String original) {
        int hyphenIndex = original.indexOf('-');
        if (hyphenIndex != -1) {
            return original.substring(0, hyphenIndex-1).trim();
        }
        return original;
    }

    public static String removeSubtitle2(String title, String subTitle) {
        int titleLength = title.length();
        int subTitleLength = subTitle.length();
        if (subTitleLength == 0){
            return title;
        }
        return title.substring(0, titleLength-subTitleLength-3).trim();
    }

    public static String extractAuthors(String original) {
        String[] parts = original.split(",");
        StringBuilder authors = new StringBuilder();

        for (String part : parts) {
            part = part.trim();
            if (!part.contains("(")) {
                authors.append(part).append(", ");
            } else {
                int bracketIndex = part.indexOf('(');
                authors.append(part.substring(0, bracketIndex).trim()).append(", ");
                break;
            }
        }
        if (authors.length() > 0) {
            authors.setLength(authors.length() - 2);
        }
        return authors.toString();
    }


}