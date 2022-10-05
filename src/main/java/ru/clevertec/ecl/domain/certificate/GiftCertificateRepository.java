package ru.clevertec.ecl.domain.certificate;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Integer>{

    @EntityGraph(attributePaths = {"tags"},type = EntityGraph.EntityGraphType.LOAD)
    @Query("select g from GiftCertificate as g join g.tags as t where t.name = :name")
    List<GiftCertificate> findAllByTagName(String name, Pageable pageable);
}
