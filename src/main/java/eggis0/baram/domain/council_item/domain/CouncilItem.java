package eggis0.baram.domain.council_item.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import eggis0.baram.domain.council.domain.Council;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
@AllArgsConstructor
public class CouncilItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemId;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private ItemType type;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "councilId")
    @JsonIgnore
    private Council council;
}
