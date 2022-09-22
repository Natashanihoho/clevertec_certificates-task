package ru.clevertec.hardziyevich.domain.tags;

import lombok.*;
import ru.clevertec.hardziyevich.domain.certificates.GiftCertificate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
    private Set<GiftCertificate> giftCertificates = new HashSet<>();

    public void addGiftCertificates(GiftCertificate giftCertificate) {
        giftCertificates.add(giftCertificate);
    }
}
