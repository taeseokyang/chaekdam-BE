package com.example.subsub.dto;

import com.example.subsub.domain.Property;
import com.example.subsub.domain.Subject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PropertyDTO {

    @NotNull(message = "subjectId cannot be null")
    @Schema(description = "과목 고유번호 자동 부여")
    private Integer propertyId;

    @Schema(description = "마감 시간", nullable = false, example = "20230115")
    private LocalDate expiredAt;

    @Schema(description = "ToDoList 내용", nullable = false, example = "팀플-인스타그램 구현하기")
    private String content;

    @Schema(description = "과목 이름-자동생성")
    private Subject subject;

    public static PropertyDTO toPropertyDto(Property property){
        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.propertyId = property.getPropertyId();
        propertyDTO.expiredAt = property.getExpiredAt();
        propertyDTO.content = property.getContent();
        propertyDTO.subject = property.getSubject();
        return propertyDTO;
    }
}
