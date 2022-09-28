package ru.clevertec.hardziyevich.domain.certificate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Integer> {

    Optional<GiftCertificate> findByName(String name);

    Optional<GiftCertificate> findFirstByDescriptionContains(String descriptionPart);

    @Query("select g from GiftCertificate as g join Tag as t on t.name = :name")
    List<GiftCertificate> findAllByTagName(String name);


}
