package com.example.subsub.dto.response;

import com.example.subsub.domain.Property;
import com.example.subsub.domain.Subject;
import com.example.subsub.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class SubjectResponse {

    private final int subjectid;
    private final String subjectName;
    private final String professorName;
    private final boolean mon;
    private final boolean tue;
    private final boolean wed;
    private final boolean thu;
    private final boolean fri;
    private final String classType;
    private final String color;
    private final String fileName;
    private final String filePath;
//    private final List<PropertyResponse> properties;
    private final List<Property> properties;
    private final String userId;

    @Builder
    public SubjectResponse(Subject subject){
        subjectid = subject.getSubjectId();
        subjectName = subject.getSubjectName();
        professorName = subject.getProfessorName();
        mon = subject.isMon();
        tue = subject.isTue();
        wed = subject.isWed();
        thu = subject.isThu();
        fri = subject.isFri();
        classType = subject.getClassType();
        color = subject.getColor();
        fileName = subject.getFileName();
        filePath = subject.getFilePath();
//        properties = PropertyResponse.toList(subject.getProperties());
        properties = getProperties();
        userId = subject.getUser().getUserId();
    }
}
