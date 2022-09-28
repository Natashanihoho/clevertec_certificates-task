package ru.clevertec.hardziyevich.api.tag;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TagReadDto {

    private Integer id;
    private String name;
    private List<GiftCertificateDto> giftCertificates;

    @Data
    public static class GiftCertificateDto {
        private Integer id;
        private String name;
        private String description;
        private Double price;
        private Integer duration;
        private LocalDateTime createDate;
        private LocalDateTime lastUpdateDate;
    }

}
