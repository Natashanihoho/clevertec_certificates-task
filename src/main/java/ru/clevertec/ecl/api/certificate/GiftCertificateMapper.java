package ru.clevertec.ecl.api.certificate;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.clevertec.ecl.api.tag.TagMapper;
import ru.clevertec.ecl.domain.certificate.GiftCertificate;

@Mapper(uses = TagMapper.class)
public interface GiftCertificateMapper {

    GiftCertificateReadDto mapToGiftCertificateReadDto(GiftCertificate giftCertificate);

    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "lastUpdateDate", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    GiftCertificate mapToGiftCertificate(GiftCertificatePostDto giftCertificatePostDto);
}
