package ru.clevertec.ecl.domain.order;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.api.order.OrderPostDto;
import ru.clevertec.ecl.api.order.OrderReadDto;
import ru.clevertec.ecl.api.order.OrderSequenceDto;

import java.util.List;

public interface OrderService {

    OrderReadDto findById(Integer id);

    List<OrderReadDto> findAll(Pageable pageable);

    List<OrderReadDto> findAllById(Integer min, Integer max);

    OrderReadDto makeOrder(OrderPostDto orderPostDto);

    OrderSequenceDto sequence();
}
