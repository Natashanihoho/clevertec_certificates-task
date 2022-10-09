package ru.clevertec.ecl.domain.order;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.api.exception.EntityNotFoundException;
import ru.clevertec.ecl.api.exception.ErrorCode;
import ru.clevertec.ecl.api.order.OrderMapper;
import ru.clevertec.ecl.api.order.OrderPostDto;
import ru.clevertec.ecl.api.order.OrderReadDto;
import ru.clevertec.ecl.domain.certificate.GiftCertificate;
import ru.clevertec.ecl.domain.certificate.GiftCertificateRepository;
import ru.clevertec.ecl.domain.user.User;
import ru.clevertec.ecl.domain.user.UserRepository;

import java.util.List;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    private final GiftCertificateRepository giftCertificateRepository;
    private final UserRepository userRepository;

    @Override
    public OrderReadDto findById(Integer id) {
        return orderRepository.findById(id)
                .map(orderMapper::mapToOrderReadDto)
                .orElseThrow(exceptionSupplier("Order is not found with id = " + id));
    }

    @Override
    public List<OrderReadDto> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .stream()
                .map(orderMapper::mapToOrderReadDto)
                .collect(toList());
    }

    @Transactional
    @Override
    public OrderReadDto makeOrder(OrderPostDto orderPostDto) {
        GiftCertificate giftCertificate = giftCertificateRepository.findById(orderPostDto.getGiftCertificateId())
                .orElseThrow(exceptionSupplier("Certificate in order is not found with id = "
                        + orderPostDto.getGiftCertificateId()));
        User user = userRepository.findById(orderPostDto.getUserId())
                .orElseThrow(exceptionSupplier("User in order is not found with id = "
                        + orderPostDto.getUserId()));
        Order order = orderRepository.save(orderMapper.mapToOrder(user, giftCertificate));
        return orderMapper.mapToOrderReadDto(order);
    }

    private Supplier<EntityNotFoundException> exceptionSupplier(String message) {
        return () -> new EntityNotFoundException(message, ErrorCode.ORDER);
    }
}
