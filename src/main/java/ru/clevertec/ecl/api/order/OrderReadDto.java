package ru.clevertec.ecl.api.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.clevertec.ecl.api.certificate.GiftCertificateReadDto;
import ru.clevertec.ecl.api.user.UserReadDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderReadDto {

    private Integer id;
    private GiftCertificateReadDto giftCertificateReadDto;
    private UserReadDto userReadDto;
    private BigDecimal cost;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime purchaseDate;
}
