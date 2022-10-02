package ru.clevertec.hardziyevich.api.certificate;

import org.mapstruct.Mapper;
import ru.clevertec.hardziyevich.domain.certificate.GiftCertificate;

@Mapper(componentModel = "spring")
public interface GiftCertificateMapper {
    GiftCertificateReadDto mapToGiftCertificateReadDto(GiftCertificate giftCertificate);
    GiftCertificate mapToGiftCertificate(GiftCertificatePostDto giftCertificatePostDto);

}
