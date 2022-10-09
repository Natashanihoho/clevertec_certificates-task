package ru.clevertec.ecl.api.order;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
public class OrderPostDto {

    @NotNull
    @Positive
    private Integer giftCertificateId;

    @NotNull
    @Positive
    private Integer userId;
}
