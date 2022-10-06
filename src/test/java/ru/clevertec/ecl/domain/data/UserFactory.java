package ru.clevertec.ecl.domain.data;

import org.junit.jupiter.api.Test;
import ru.clevertec.ecl.api.user.UserReadDto;
import ru.clevertec.ecl.domain.user.User;

public class UserFactory {

    public static User user() {
        return User.builder()
                .id(1)
                .firstname("Firstname1")
                .lastname("Lastname1")
                .build();
    }

    public static UserReadDto userReadDto() {
        return UserReadDto.builder()
                .id(1)
                .firstname("Firstname1")
                .lastname("Lastname1")
                .build();
    }
}
