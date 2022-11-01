package ru.clevertec.ecl.api.order;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.domain.order.OrderService;
import ru.clevertec.ecl.infrastructure.node.Node;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderReadDto> makeOrder(@RequestBody @Valid OrderPostDto orderPostDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.makeOrder(orderPostDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderReadDto> findById(@PathVariable @Positive Integer id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<OrderReadDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(orderService.findAll(pageable));
    }
}
