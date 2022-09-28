package ru.clevertec.hardziyevich.domain.certificate;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.clevertec.hardziyevich.configuration.RepositoryConfiguration;

import java.util.Optional;

public class CertificateRepositoryTest extends RepositoryConfiguration {

    @Autowired
    private GiftCertificateRepository giftCertificateRepository;

    @Test
    public void checkFindByName() {
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .name("Certificate1")
                .description("Some description")
                .price(1.2)
                .duration(1)
                .build();

        giftCertificateRepository.save(giftCertificate);

        Optional<GiftCertificate> certificateOptional = giftCertificateRepository.findByName("Certificate1");
        Assertions.assertTrue(certificateOptional.isPresent());
    }

    @Test
    public void checkFindByNameIfCertificateIsAbsent() {

        Optional<GiftCertificate> certificateOptional = giftCertificateRepository.findByName("Certificate2");
        Assertions.assertFalse(certificateOptional.isPresent());
    }

    @Test
    public void checkFindFirstByDescriptionContains() {
        Optional<GiftCertificate> giftCertificate = giftCertificateRepository.findFirstByDescriptionContains("descript");
        Assertions.assertTrue(giftCertificate.isPresent());
    }
}
