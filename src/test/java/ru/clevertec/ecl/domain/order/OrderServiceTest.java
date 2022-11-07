package ru.clevertec.ecl.domain.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.clevertec.ecl.api.certificate.GiftCertificateMapper;
import ru.clevertec.ecl.api.exception.EntityNotFoundException;
import ru.clevertec.ecl.api.order.OrderMapper;
import ru.clevertec.ecl.api.order.OrderPostDto;
import ru.clevertec.ecl.api.order.OrderReadDto;
import ru.clevertec.ecl.api.tag.TagMapper;
import ru.clevertec.ecl.domain.certificate.GiftCertificate;
import ru.clevertec.ecl.domain.certificate.GiftCertificateRepository;
import ru.clevertec.ecl.domain.data.CertificateFactory;
import ru.clevertec.ecl.domain.data.OrderFactory;
import ru.clevertec.ecl.domain.data.UserFactory;
import ru.clevertec.ecl.domain.tag.TagRepository;
import ru.clevertec.ecl.domain.user.User;
import ru.clevertec.ecl.domain.user.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private GiftCertificateRepository giftCertificateRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GiftCertificateMapper giftCertificateMapper;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private TagMapper tagMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order order;
    private OrderReadDto orderReadDto;
    private OrderPostDto orderPostDto;

    @BeforeEach
    void init() {
        order = OrderFactory.order();
        orderReadDto = OrderFactory.orderReadDto();
        orderPostDto = OrderFactory.orderPostDto();
    }

    @Test
    public void findByIdTest() {
        when(orderMapper.mapToOrderReadDto(order))
                .thenReturn(orderReadDto);
        when(orderRepository.findById(1))
                .thenReturn(Optional.ofNullable(order));
        assertEquals(orderReadDto, orderService.findById(1));
    }

    @Test
    public void findByAbsentIdTest() {
        when(orderRepository.findById(2))
                .thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> orderService.findById(2));
    }

    @Test
    public void findAllTest() {
        PageRequest pageable = PageRequest.of(0, 2);
        List<Order> orders = Arrays.asList(order, order);
        when(orderRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(orders));
        when(orderMapper.mapToOrderReadDto(order))
                .thenReturn(orderReadDto, orderReadDto);
        List<OrderReadDto> all = orderService.findAll(pageable);
        assertEquals(2, all.size());
        assertEquals(orderReadDto, all.get(0));
    }

    @Test
    public void makeOrderTest() {
        GiftCertificate giftCertificate = CertificateFactory.giftCertificate();
        User user = UserFactory.user();
        when(giftCertificateRepository.findById(orderPostDto.getGiftCertificateId()))
                .thenReturn(Optional.of(giftCertificate));
        when(userRepository.findById(orderPostDto.getUserId()))
                .thenReturn(Optional.of(user));
        when(orderMapper.mapToOrder(user, giftCertificate))
                .thenReturn(order);
        orderService.makeOrder(orderPostDto);
        verify(orderRepository).save(order);
    }
}
