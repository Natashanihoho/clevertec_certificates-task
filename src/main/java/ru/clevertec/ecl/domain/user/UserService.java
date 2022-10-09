package ru.clevertec.ecl.domain.user;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.api.user.UserReadDto;

import java.util.List;

public interface UserService {

    UserReadDto findById(Integer id);

    List<UserReadDto> findAll(Pageable pageable);
}
