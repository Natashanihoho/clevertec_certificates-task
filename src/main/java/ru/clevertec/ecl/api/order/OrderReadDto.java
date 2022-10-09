package ru.clevertec.ecl.api.order;

import lombok.Builder;
import lombok.Data;
import ru.clevertec.ecl.api.certificate.GiftCertificateReadDto;
import ru.clevertec.ecl.api.user.UserReadDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class OrderReadDto {

    private Integer id;
    private GiftCertificateReadDto giftCertificateReadDto;
    private UserReadDto userReadDto;
    private BigDecimal cost;
    private LocalDateTime purchaseDate;
}
