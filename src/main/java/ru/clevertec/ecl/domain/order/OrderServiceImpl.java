package ru.clevertec.ecl.domain.order;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.function.Supplier;
import javax.annotation.PostConstruct;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ru.clevertec.ecl.api.exception.EntityNotFoundException;
import ru.clevertec.ecl.api.exception.ErrorCode;
import ru.clevertec.ecl.api.order.OrderMapper;
import ru.clevertec.ecl.api.order.OrderPostDto;
import ru.clevertec.ecl.api.order.OrderReadDto;
import ru.clevertec.ecl.api.order.OrderSequenceDto;
import ru.clevertec.ecl.infrastructure.node.Node;

@Log4j2
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final Node node;

    @PostConstruct
    void setStartValueId() {
        log.info(orderRepository.setSequence(node.getNumber()));
        node.getSubNodes().forEach(sub -> log.info(sub.getUrl()));
    }

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

    @Override
    public List<OrderReadDto> findAllById(Integer min, Integer max) {
        return orderRepository.findAllByIdGreaterThanAndIdLessThanEqual(min,max)
                .stream()
                .map(orderMapper::mapToOrderReadDto)
                .collect(toList());
    }

    @Transactional
    @Override
    public OrderReadDto makeOrder(OrderPostDto orderPostDto) {
        GiftCertificateAndUser giftCertificateAndUser = orderRepository.findGiftCertificateAndUser(
                        orderPostDto.getUserId(), orderPostDto.getGiftCertificateId())
                .orElseThrow(exceptionSupplier("User or Certificate in order are not found"));
        return orderMapper.mapToOrderReadDto(
                orderRepository.save(
                        orderMapper.mapToOrder(
                                giftCertificateAndUser.getUser(), giftCertificateAndUser.getGiftCertificate()
                        )
                )
        );
    }

    @Override
    public OrderSequenceDto sequence() {
        return OrderSequenceDto.builder()
                .sequence(orderRepository.sequence())
                .isFirstOrderSaved(orderRepository.existsById(node.getNumber()))
                .build();
    }

    private Supplier<EntityNotFoundException> exceptionSupplier(String message) {
        return () -> new EntityNotFoundException(message, ErrorCode.ORDER);
    }
}
