package ru.clevertec.ecl.domain.certificate;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.clevertec.ecl.api.certificate.GiftCertificatePostDto;
import ru.clevertec.ecl.api.tag.TagMapper;

@Mapper(uses = TagMapper.class, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UpdateMapper {

    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "lastUpdateDate", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    GiftCertificate update(@MappingTarget GiftCertificate giftCertificate, GiftCertificatePostDto giftCertificatePostDto);
}
