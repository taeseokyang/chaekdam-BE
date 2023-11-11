package com.example.subsub.domain;
import com.example.subsub.dto.request.AddPropertyRequest;
import com.example.subsub.dto.request.UpdatePropertyRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer propertyId;

    @Column(nullable = false)
    private LocalDate expiredAt;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subjectId")
    @JsonIgnore
    private Subject subject;

    public static Property of(AddPropertyRequest request, Subject subject){
        return Property.builder()
                .expiredAt(request.getExpiredAt())
                .content(request.getContent())
                .subject(subject)
                .build();
    }

    public static Property from(AddPropertyRequest request){
        return Property.builder()
                .expiredAt(request.getExpiredAt())
                .content(request.getContent())
                .build();
    }

    public void update(UpdatePropertyRequest request){
        this.expiredAt = request.getExpiredAt();
        this.content = request.getContent();
    }
}
