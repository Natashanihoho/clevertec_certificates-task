package ru.clevertec.ecl.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.clevertec.ecl.api.exception.EntityNotFoundException;
import ru.clevertec.ecl.api.user.UserMapper;
import ru.clevertec.ecl.api.user.UserReadDto;
import ru.clevertec.ecl.domain.data.UserFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserReadDto userReadDto;

    @BeforeEach
    void init() {
        user = UserFactory.user();
        userReadDto = UserFactory.userReadDto();
    }

    @Test
    public void findByIdTest() {
        when(userMapper.mapToUserReadDto(user))
                .thenReturn(userReadDto);
        when(userRepository.findById(1))
                .thenReturn(Optional.ofNullable(user));
        assertEquals(userReadDto, userService.findById(1));
    }

    @Test
    public void findByAbsentIdTest() {
        when(userRepository.findById(2))
                .thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.findById(2));
    }

    @Test
    public void findAllTest() {
        PageRequest pageable = PageRequest.of(0, 2);
        List<User> users = Arrays.asList(user, user);
        when(userRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(users));
        when(userMapper.mapToUserReadDto(user))
                .thenReturn(userReadDto, userReadDto);
        List<UserReadDto> all = userService.findAll(pageable);
        assertEquals(2, all.size());
        assertEquals(userReadDto, all.get(0));
    }
}
