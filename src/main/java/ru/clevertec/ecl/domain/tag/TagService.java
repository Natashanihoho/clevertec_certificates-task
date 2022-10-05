package ru.clevertec.ecl.domain.tag;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.api.tag.TagPostDto;
import ru.clevertec.ecl.api.tag.TagReadDto;

import java.util.List;

public interface TagService {

    TagReadDto create(TagPostDto tagPostDto);

    void delete(Integer id);

    TagReadDto findById(Integer id);

    List<TagReadDto> findAll(Pageable pageable);

    TagReadDto updateById(Integer id, TagPostDto tagPostDto);
}
