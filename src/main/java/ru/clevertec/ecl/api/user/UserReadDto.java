package ru.clevertec.ecl.api.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserReadDto {

    private Integer id;
    private String firstname;
    private String lastname;
}
