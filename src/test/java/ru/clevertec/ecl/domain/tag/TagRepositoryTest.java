package ru.clevertec.ecl.domain.tag;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.clevertec.ecl.domain.data.TagFactory;
import ru.clevertec.ecl.integration.testcontainer.RepositoryConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagRepositoryTest extends RepositoryConfiguration {

    @Autowired
    private TagRepository tagRepository;

    @Test
    public void checkFindByNameInTags() {
        Tag tag = TagFactory.tag();
        List<Tag> tags = Arrays.asList(tag, tag);
        tagRepository.save(tag);
        List<Tag> existedTags = tagRepository.findByNameIn(tags.stream().map(Tag::getName).collect(toList()));
        assertEquals(1, existedTags.size());
    }

    @Test
    public void checkFindByNameInTagsIfTagsAreAbsent() {
        List<Tag> existedTags = tagRepository.findByNameIn(new ArrayList<>());
        assertEquals(0, existedTags.size());
    }
}
