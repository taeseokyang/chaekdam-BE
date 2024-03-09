package eggis0.baram.domain2.council.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eggis0.baram.domain2.council_item.domain.CouncilItem;
import eggis0.baram.domain2.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    @JsonIgnore
    private User manager;

    @Column(nullable = false)
    private String college;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String operatingHours;

    @Column(nullable = false)
    private String usageGuidelines;

    @Column(nullable = false)
    private Boolean isCouncilSelfManage;

    @OneToMany(mappedBy = "council", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<CouncilItem> items = new ArrayList<>();
}