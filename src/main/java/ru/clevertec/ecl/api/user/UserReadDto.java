package ru.clevertec.ecl.api.user;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserReadDto {

    Integer id;
    String firstname;
    String lastname;
}
