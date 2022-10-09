package ru.clevertec.ecl.api.user;

import org.mapstruct.Mapper;
import ru.clevertec.ecl.domain.user.User;

@Mapper
public interface UserMapper {

    UserReadDto mapToUserReadDto(User user);
}
