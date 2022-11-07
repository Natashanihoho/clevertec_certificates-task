package ru.clevertec.ecl.domain.data;

import ru.clevertec.ecl.api.certificate.GiftCertificatePostDto;
import ru.clevertec.ecl.api.certificate.GiftCertificateReadDto;
import ru.clevertec.ecl.api.order.OrderPostDto;
import ru.clevertec.ecl.api.order.OrderReadDto;
import ru.clevertec.ecl.api.user.UserReadDto;
import ru.clevertec.ecl.domain.certificate.GiftCertificate;
import ru.clevertec.ecl.domain.order.Order;
import ru.clevertec.ecl.domain.user.User;

import java.time.LocalDateTime;

public class OrderFactory {

    private static GiftCertificate giftCertificate = CertificateFactory.giftCertificate();
    private static GiftCertificateReadDto giftCertificateReadDto = CertificateFactory.giftCertificateReadDto();
    private static GiftCertificatePostDto giftCertificatePostDto = CertificateFactory.giftCertificatePostDto();

    private static User user = UserFactory.user();
    private static UserReadDto userReadDto = UserFactory.userReadDto();

    public static Order order() {
        return Order.builder()
                .id(1)
                .giftCertificate(giftCertificate)
                .user(user)
                .cost(giftCertificate.getPrice())
                .purchaseDate(LocalDateTime.now())
                .build();
    }

    public static OrderReadDto orderReadDto() {
        return OrderReadDto.builder()
                .id(1)
                .giftCertificateReadDto(giftCertificateReadDto)
                .userReadDto(userReadDto)
                .cost(giftCertificateReadDto.getPrice())
                .purchaseDate(LocalDateTime.now())
                .build();
    }

    public static OrderPostDto orderPostDto() {
        return OrderPostDto.builder()
                .giftCertificateId(1)
                .userId(1)
                .build();
    }
}
