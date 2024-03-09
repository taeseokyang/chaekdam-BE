package eggis0.baram.domain.visit_count.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
@AllArgsConstructor
public class VisitCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer visitCountId;

    @Column(nullable = false)
    private Integer count;

    @Column(nullable = false)
    private LocalDate day;
}