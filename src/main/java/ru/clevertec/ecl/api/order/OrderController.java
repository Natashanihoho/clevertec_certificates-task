package ru.clevertec.ecl.api.order;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import ru.clevertec.ecl.domain.order.OrderService;

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

    @GetMapping("/id")
    public ResponseEntity<List<OrderReadDto>> findAllWithId(@RequestParam Integer min, @RequestParam Integer max) {
        return ResponseEntity.ok(orderService.findAllById(min, max));
    }

    @GetMapping("/sequence")
    public ResponseEntity<OrderSequenceDto> sequence() {
        return ResponseEntity.ok(orderService.sequence());
    }
}
