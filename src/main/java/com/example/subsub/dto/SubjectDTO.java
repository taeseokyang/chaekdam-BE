package com.example.subsub.dto;

import com.example.subsub.domain.Property;
import com.example.subsub.domain.Subject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@Data
public class SubjectDTO {

    @NotNull(message = "subjectId cannot be null")
    @Schema(description = "과목 고유번호 자동 부여")
    private Integer subjectId;

    @Schema(description = "과목 이름", nullable = false, example = "데이터베이스")
    private String subjectName;

    @Schema(description = "교수님 성함", nullable = false, example = "최아영교수님")
    private String professorName;

    @Schema(description = "과목 시간", nullable = false, example = "월요일")
    private String date;

    private boolean mon;
    private boolean tue;
    private boolean wed;
    private boolean thurs;
    private boolean fri;

    @Schema(description = "수업 종류", nullable = false, example = "전공필수")
    private String classType;

    @Schema(description = "과목 고유 색상", nullable = false, example = "#FFFFFF")
    private String color;

    @Schema(description = "파일명", nullable = false, example = "픽토그램.png")
    private String fileName;

    @Schema(description = "파일 경로", nullable = false, example = "C:\\Users\\바탕 화면\\배경화면")
    private String filePath;


    private List<Property> properties;

//    public static SubjectDTO toSubjectDto(Subject subject){
//        SubjectDTO subjectDTO = new SubjectDTO();
//        subjectDTO.subjectId = subject.getSubjectId();
//        subjectDTO.subjectName = subject.getSubjectName();
//        subjectDTO.professorName = subject.getProfessorName();
//        subjectDTO.date = subject.getDate();
//        subjectDTO.classType = subject.getClassType();
//        subjectDTO.color = subject.getColor();
//        subjectDTO.fileName = subject.getFileName();
//        subjectDTO.filePath = subject.getFilePath();
//        subjectDTO.properties = subject.getProperties();
//        return subjectDTO;
//    }

}
