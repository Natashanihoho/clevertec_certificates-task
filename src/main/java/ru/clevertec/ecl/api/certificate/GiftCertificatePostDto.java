package ru.clevertec.ecl.api.certificate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.clevertec.ecl.api.tag.TagPostDto;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiftCertificatePostDto {

    private String name;

    private String description;

    @Positive
    private BigDecimal price;

    @Positive
    private Integer duration;

    private List<TagPostDto> tags;
}
