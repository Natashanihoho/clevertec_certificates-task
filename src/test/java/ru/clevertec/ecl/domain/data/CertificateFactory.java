package ru.clevertec.ecl.domain.data;

import ru.clevertec.ecl.api.certificate.GiftCertificatePostDto;
import ru.clevertec.ecl.api.certificate.GiftCertificateReadDto;
import ru.clevertec.ecl.api.tag.TagPostDto;
import ru.clevertec.ecl.domain.certificate.GiftCertificate;
import ru.clevertec.ecl.domain.tag.Tag;

import java.math.BigDecimal;
import java.util.Arrays;

public class CertificateFactory {

    public static GiftCertificatePostDto giftCertificatePostDto() {
        TagPostDto tag1 = new TagPostDto();
        tag1.setName("tag1");
        TagPostDto tag2 = new TagPostDto();
        tag2.setName("tag2");
        return GiftCertificatePostDto.builder()
                .name("test_name")
                .description("test_description")
                .price(new BigDecimal("1.0"))
                .duration(1)
                .tags(Arrays.asList(tag1, tag2))
                .build();
    }

    public static GiftCertificateReadDto giftCertificateReadDto() {
        return GiftCertificateReadDto.builder()
                .id(1)
                .name("test_name")
                .description("test_description")
                .price(new BigDecimal("1.0"))
                .duration(1)
                .build();
    }

    public static GiftCertificate giftCertificate() {
        Tag tag1 = new Tag();
        tag1.setName("tag1");
        Tag tag2 = new Tag();
        tag2.setName("tag2");
        return GiftCertificate.builder()
                .name("test_name")
                .description("test_description")
                .price(new BigDecimal("1.0"))
                .duration(1)
                .tags(Arrays.asList(tag1, tag2))
                .build();
    }
}
