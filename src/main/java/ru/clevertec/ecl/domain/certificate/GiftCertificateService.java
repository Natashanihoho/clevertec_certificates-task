package ru.clevertec.ecl.domain.certificate;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.api.certificate.GiftCertificatePostDto;
import ru.clevertec.ecl.api.certificate.GiftCertificateReadDto;

import java.util.List;

public interface GiftCertificateService {

    GiftCertificateReadDto create(GiftCertificatePostDto giftCertificatePostDto);

    void delete(Integer id);

    GiftCertificateReadDto findById(Integer id);

    List<GiftCertificateReadDto> findAll(String name, String description, Pageable pageable);

    GiftCertificateReadDto updateById(Integer id, GiftCertificatePostDto giftCertificatePostDto);

    List<GiftCertificateReadDto> findAllByTagNames(List<String> tagNames, Pageable pageable);
}
