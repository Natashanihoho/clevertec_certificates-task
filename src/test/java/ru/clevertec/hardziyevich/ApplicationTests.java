package ru.clevertec.hardziyevich;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.clevertec.hardziyevich.configuration.IT;
import ru.clevertec.hardziyevich.domain.tag.Tag;
import ru.clevertec.hardziyevich.domain.tag.TagRepository;

@IT
class ApplicationTests {

	@Autowired
	TagRepository tagRepository;

	@Test
	void contextLoads() {
		Tag tag = tagRepository.save(Tag.builder()
				.name("testcontainer")
				.build()
		);
		System.out.println(tag.getName() + " id:" + tag.getId());
	}

}
