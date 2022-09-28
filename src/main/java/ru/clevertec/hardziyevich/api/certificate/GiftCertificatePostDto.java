package ru.clevertec.hardziyevich.api.certificate;

import lombok.*;
import ru.clevertec.hardziyevich.api.tag.TagPostDto;
import java.util.List;
@Data
@Builder
public class GiftCertificatePostDto {
    private String name;
    private String description;
    private Double price;
    private Integer duration;
    private List<TagPostDto> tags;

}
