package com.example.subsub.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Council {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer councilId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String operatingHours;

    @Column(nullable = false)
    private String usageGuidelines;

    @OneToMany(mappedBy = "council", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<CouncilItem> items = new ArrayList<>();
}