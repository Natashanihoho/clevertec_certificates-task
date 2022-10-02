package ru.clevertec.hardziyevich.domain.tag;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.clevertec.hardziyevich.testcontainer.RepositoryConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagRepositoryTest extends RepositoryConfiguration {

    @Autowired
    private TagRepository tagRepository;

    @Test
    public void checkFindByNameInTags() {
        Tag tag = TagFactory.tag();
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);
        tagRepository.save(tag);
        List<Tag> existedTags = tagRepository.findByNameIn(tags.stream().map(Tag::getName).collect(Collectors.toList()));
        assertEquals(1, existedTags.size());
    }

    @Test
    public void checkFindByNameInTagsIfTagsAreAbsent() {
        List<Tag> existedTags = tagRepository.findByNameIn(new ArrayList<>());
        assertEquals(0, existedTags.size());
    }
}
