package ru.clevertec.hardziyevich.api.tag;

import org.mapstruct.Mapper;
import ru.clevertec.hardziyevich.domain.tag.Tag;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagReadDto mapToTagReadDto(Tag tag);

    Tag mapToTag(TagPostDto tagPostDto);
}
