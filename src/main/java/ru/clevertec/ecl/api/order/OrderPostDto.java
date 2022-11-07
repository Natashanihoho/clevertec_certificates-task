package ru.clevertec.ecl.api.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderPostDto {

    @NotNull
    @Positive
    private Integer giftCertificateId;

    @NotNull
    @Positive
    private Integer userId;
}
