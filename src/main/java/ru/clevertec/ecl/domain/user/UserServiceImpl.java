package ru.clevertec.ecl.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.api.exception.EntityNotFoundException;
import ru.clevertec.ecl.api.exception.ErrorCode;
import ru.clevertec.ecl.api.user.UserMapper;
import ru.clevertec.ecl.api.user.UserReadDto;

import java.util.List;
import java.util.function.Supplier;

import static java.util.stream.Collectors.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public UserReadDto findById(Integer id) {
        return userRepository.findById(id)
                .map(userMapper::mapToUserReadDto)
                .orElseThrow(() -> new EntityNotFoundException("User is not found with id = " + id, ErrorCode.USER));
    }

    @Override
    public List<UserReadDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .stream()
                .map(userMapper::mapToUserReadDto)
                .collect(toList());
    }

    private Supplier<EntityNotFoundException> exceptionSupplier(Integer id) {
        return () -> new EntityNotFoundException(
                "User is not found with id = " + id,
                ErrorCode.USER
        );
    }
}
