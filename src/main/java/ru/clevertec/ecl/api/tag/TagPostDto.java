package ru.clevertec.ecl.api.tag;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TagPostDto {

    @NotBlank
    private String name;
}
