package eggis0.baram.domain.gachon_herald.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class GachonHerald {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gachonHeraldId;

    @Column
    private String phoneNumber;

    @Column
    private String opinion;
}