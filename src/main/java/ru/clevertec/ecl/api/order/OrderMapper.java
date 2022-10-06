package ru.clevertec.ecl.api.order;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.clevertec.ecl.api.certificate.GiftCertificateMapper;
import ru.clevertec.ecl.api.user.UserMapper;
import ru.clevertec.ecl.domain.certificate.GiftCertificate;
import ru.clevertec.ecl.domain.order.Order;
import ru.clevertec.ecl.domain.user.User;

@Mapper(uses = {UserMapper.class, GiftCertificateMapper.class})
public interface OrderMapper {

    @Mapping(target = "purchaseDate", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "giftCertificate", source = "giftCertificate")
    @Mapping(target = "cost", source = "giftCertificate.price")
    Order mapToOrder(User user, GiftCertificate giftCertificate);

    @Mapping(target = "userReadDto", source = "order.user")
    @Mapping(target = "giftCertificateReadDto", source = "order.giftCertificate")
    OrderReadDto mapToOrderReadDto(Order order);
}
