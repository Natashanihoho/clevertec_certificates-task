package ru.clevertec.hardziyevich.domain.certificate;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Integer> {

    @Query("select g from GiftCertificate as g join Tag as t on t.name = :name")
    List<GiftCertificate> findAllByTagName(String name, Pageable pageable);
}
