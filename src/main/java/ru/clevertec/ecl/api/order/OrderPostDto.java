package ru.clevertec.ecl.api.order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderPostDto {

    private Integer giftCertificateId;
    private Integer userId;
}
