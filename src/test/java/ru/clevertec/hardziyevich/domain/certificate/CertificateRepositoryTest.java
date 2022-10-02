package ru.clevertec.hardziyevich.domain.certificate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import ru.clevertec.hardziyevich.testcontainer.RepositoryConfiguration;
import ru.clevertec.hardziyevich.domain.tag.TagRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CertificateRepositoryTest extends RepositoryConfiguration {

    @Autowired
    private GiftCertificateRepository giftCertificateRepository;

    @Autowired
    private TagRepository tagRepository;

    private final GiftCertificate giftCertificate = CertificateFactory.giftCertificate();

    @Test
    public void checkFindByName() {
        tagRepository.saveAll(giftCertificate.getTags());
        giftCertificateRepository.save(giftCertificate);
        String name = giftCertificate.getTags().get(0).getName();
        List<GiftCertificate> allByTagName = giftCertificateRepository.findAllByTagName(name, Pageable.ofSize(1));
        allByTagName.forEach(System.out::println);
        assertEquals(1, allByTagName.size());
    }

}
