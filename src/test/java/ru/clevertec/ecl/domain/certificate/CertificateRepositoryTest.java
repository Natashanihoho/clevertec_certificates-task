package ru.clevertec.ecl.domain.certificate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.domain.data.CertificateFactory;
import ru.clevertec.ecl.integration.testcontainer.RepositoryConfiguration;
import ru.clevertec.ecl.domain.tag.TagRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CertificateRepositoryTest extends RepositoryConfiguration {

    @Autowired
    private GiftCertificateRepository giftCertificateRepository;

    @Autowired
    private TagRepository tagRepository;

    private final GiftCertificate giftCertificate = CertificateFactory.giftCertificate();

    @Test
    public void checkFindByTagNames() {
        tagRepository.saveAll(giftCertificate.getTags());
        giftCertificateRepository.save(giftCertificate);
        List<String> tagNames = Arrays.asList("tag1", "tag2");
        List<GiftCertificate> allByTagName = giftCertificateRepository.findAllByTagNames(
                tagNames, Pageable.ofSize(1)
        );
        assertEquals(1, allByTagName.size());
    }
}
