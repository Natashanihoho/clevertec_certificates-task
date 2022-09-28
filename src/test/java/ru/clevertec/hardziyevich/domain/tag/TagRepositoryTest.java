package ru.clevertec.hardziyevich.domain.tag;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.clevertec.hardziyevich.api.tag.TagPostDto;
import ru.clevertec.hardziyevich.configuration.RepositoryConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class TagRepositoryTest extends RepositoryConfiguration {

    @Autowired
    private TagRepository tagRepository;

    private  List<String> tagPostDtoListNames;

    @BeforeEach
    public void init() {
        TagPostDto tagPostDto1 = new TagPostDto();
        tagPostDto1.setName("tag1");
        TagPostDto tagPostDto2 = new TagPostDto();
        tagPostDto2.setName("tag2");
        tagPostDtoListNames = Arrays.asList(
                        tagPostDto1,
                        tagPostDto2
                )
                .stream()
                .map(tagPostDto -> tagPostDto.getName())
                .collect(Collectors.toList());
    }

    @Test
    public void checkFindByNameInTags() {
        Tag tag1 = new Tag();
        tag1.setName("tag1");
        tagRepository.save(tag1);

        List<Tag> existedTags = tagRepository.findByNameIn(tagPostDtoListNames);
        assertTrue(existedTags.size() == 1);
    }

    @Test
    public void checkFindByNameInTagsIfTagsAreAbsent() {

        List<Tag> existedTags = tagRepository.findByNameIn(tagPostDtoListNames);
        assertTrue(existedTags.size() == 0);
    }
}
