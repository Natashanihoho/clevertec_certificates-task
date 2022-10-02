package ru.clevertec.hardziyevich.api.certificate;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class GiftCertificateReadDto {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Integer duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private List<TagDto> tags;

    @Data
    public static class TagDto {
        private Integer id;
        private String name;
    }

}
