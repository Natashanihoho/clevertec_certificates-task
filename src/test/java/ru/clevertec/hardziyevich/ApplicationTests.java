package ru.clevertec.hardziyevich;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.clevertec.hardziyevich.configuration.RepositoryConfiguration;
import ru.clevertec.hardziyevich.domain.tag.Tag;
import ru.clevertec.hardziyevich.domain.tag.TagRepository;

class ApplicationTests extends RepositoryConfiguration {

	@Autowired
	TagRepository tagRepository;

	@Test
	void contextLoads() {
		Tag tag = tagRepository.save(Tag.builder()
				.name("testTag")
				.build()
		);
		Assertions.assertNotNull(tag);
		Assertions.assertTrue(tag.getId() > 0);
		Assertions.assertEquals("testTag", tag.getName());
	}

}
