package ru.clevertec.ecl.domain.order;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.api.exception.EntityNotFoundException;
import ru.clevertec.ecl.api.exception.ErrorCode;
import ru.clevertec.ecl.api.order.OrderPostDto;
import ru.clevertec.ecl.api.order.OrderReadDto;

import java.util.List;
import java.util.function.Supplier;

public class OrderServiceImpl implements OrderService {

    @Override
    public OrderReadDto findById(Integer id) {
        return null;
    }

    @Override
    public List<OrderReadDto> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public OrderReadDto makeOrder(OrderPostDto orderPostDto) {
        return null;
    }

    private Supplier<EntityNotFoundException> exceptionSupplier(Integer id) {
        return () -> new EntityNotFoundException(
                "Order is not found with id = " + id,
                ErrorCode.ORDER
        );
    }
}
