package ru.clevertec.hardziyevich.domain.tag;

import ru.clevertec.hardziyevich.api.tag.TagPostDto;
import ru.clevertec.hardziyevich.api.tag.TagReadDto;

import java.util.Collections;

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
        tag.setGiftCertificates(Collections.emptyList());
        return tag;
    }
}
