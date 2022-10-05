package ru.clevertec.ecl.api.certificate;

import lombok.Builder;
import lombok.Value;
import ru.clevertec.ecl.api.tag.TagReadDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class GiftCertificateReadDto {

    Integer id;
    String name;
    String description;
    BigDecimal price;
    Integer duration;
    LocalDateTime createDate;
    LocalDateTime lastUpdateDate;
    List<TagReadDto> tags;
}
