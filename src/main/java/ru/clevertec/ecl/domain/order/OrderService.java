package ru.clevertec.ecl.domain.order;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.api.order.OrderPostDto;
import ru.clevertec.ecl.api.order.OrderReadDto;

import java.util.List;

public interface OrderService {

    OrderReadDto findById(Integer id);

    List<OrderReadDto> findAll(Pageable pageable);

    OrderReadDto makeOrder(OrderPostDto orderPostDto);
}
