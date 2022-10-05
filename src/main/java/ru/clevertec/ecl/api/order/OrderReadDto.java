package ru.clevertec.ecl.api.order;

import lombok.Builder;
import lombok.Value;
import ru.clevertec.ecl.api.certificate.GiftCertificateReadDto;
import ru.clevertec.ecl.api.user.UserReadDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder
public class OrderReadDto {

    Integer id;
    GiftCertificateReadDto giftCertificateReadDto;
    UserReadDto userReadDto;
    BigDecimal cost;
    LocalDateTime localDateTime;
}
