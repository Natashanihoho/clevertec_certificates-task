package ru.clevertec.hardziyevich.domain.certificates;

import lombok.*;
import ru.clevertec.hardziyevich.domain.tags.Tag;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "gift_certificate")
public class GiftCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price")
    @Min(1)
    private Double price;

    @Column(name = "duration")
    @Min(1)
    Integer duration;

    @Column(name = "create_date", nullable = false)
    LocalDateTime createDate;

    @Column(name = "last_update_date")
    LocalDateTime lastUpdateDate;

    @Builder.Default
    @ManyToMany
    @JoinTable(
            name = "certificate_tag",
            joinColumns = @JoinColumn(name = "certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    public void addTag(Tag tag) {
        tags.add(tag);
    }
}
