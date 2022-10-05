package ru.clevertec.ecl.api.certificate;

import lombok.Builder;
import lombok.Value;
import ru.clevertec.ecl.api.tag.TagPostDto;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
public class GiftCertificatePostDto {

    String name;

    String description;

    @Positive
    BigDecimal price;

    @Positive
    Integer duration;

    List<TagPostDto> tags;
}
