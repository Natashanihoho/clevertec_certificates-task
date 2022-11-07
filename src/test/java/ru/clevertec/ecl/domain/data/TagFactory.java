package ru.clevertec.ecl.domain.data;

import ru.clevertec.ecl.api.tag.TagPostDto;
import ru.clevertec.ecl.api.tag.TagReadDto;
import ru.clevertec.ecl.domain.tag.Tag;

public class TagFactory {

    public static TagPostDto tagPostDto() {
        TagPostDto tagPostDto = new TagPostDto();
        tagPostDto.setName("test_dto");
        return tagPostDto;
    }

    public static TagReadDto tagReadDto() {
        TagReadDto tagReadDto = new TagReadDto();
        tagReadDto.setId(1);
        tagReadDto.setName("test_dto");
        return tagReadDto;
    }

    public static Tag tag() {
        Tag tag = new Tag();
        tag.setName("test");
        return tag;
    }
}
