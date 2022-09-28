package ru.clevertec.hardziyevich.domain.tag;

import lombok.*;
import ru.clevertec.hardziyevich.domain.certificate.GiftCertificate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Builder.Default
    @ManyToMany(mappedBy = "tags")
    private List<GiftCertificate> giftCertificates = new ArrayList<>();


}
