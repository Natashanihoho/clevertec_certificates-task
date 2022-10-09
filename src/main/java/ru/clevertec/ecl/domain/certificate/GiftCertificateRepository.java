package ru.clevertec.ecl.domain.certificate;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Integer> {

    @Query("select g from GiftCertificate as g join fetch g.tags as t where t.name in (:tagNames)")
    List<GiftCertificate> findAllByTagNames(List<String> tagNames, Pageable pageable);
}
