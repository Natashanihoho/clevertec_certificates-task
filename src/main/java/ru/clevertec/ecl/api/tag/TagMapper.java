package ru.clevertec.ecl.api.tag;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.clevertec.ecl.domain.tag.Tag;

@Mapper
public interface TagMapper {

    TagReadDto mapToTagReadDto(Tag tag);

    @Mapping(target = "id", ignore = true)
    Tag mapToTag(TagPostDto tagPostDto);
}
