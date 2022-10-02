package ru.clevertec.hardziyevich.domain.tag;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.hardziyevich.api.exception.ApplicationException;
import ru.clevertec.hardziyevich.api.tag.TagMapper;
import ru.clevertec.hardziyevich.api.tag.TagPostDto;
import ru.clevertec.hardziyevich.api.tag.TagReadDto;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @Mock
    private TagMapper tagMapper;

    @InjectMocks
    private TagService tagService;
    private TagPostDto tagPostDto;
    private Tag tag;
    private TagReadDto tagReadDto;

    @BeforeEach
    void init() {
        tagPostDto = TagFactory.tagPostDto();
        tag = TagFactory.tag();
        tagReadDto = TagFactory.tagReadDto();
    }


    @Test
    public void createTest() {
        when(tagMapper.mapToTag(tagPostDto))
                .thenReturn(tag);
        when(tagRepository.save(tag))
                .thenReturn(tag);
        when(tagMapper.mapToTagReadDto(tag))
                .thenReturn(tagReadDto);
        assertEquals(tagReadDto,tagService.create(tagPostDto));
    }

    @Test
    public void findByIdTest() {
        when(tagMapper.mapToTagReadDto(tag))
                .thenReturn(tagReadDto);
        when(tagRepository.findById(1))
                .thenReturn(Optional.ofNullable(tag));
        assertEquals(tagReadDto, tagService.findById(1));
    }

    @Test
    public void findByAbsentIdTest() {
        when(tagRepository.findById(2))
                .thenReturn(Optional.empty());
        assertThrows(ApplicationException.class, () -> tagService.findById(2));
    }

    @Test
    public void updateByIdTest() {
        when(tagRepository.findById(1))
                .thenReturn(Optional.of(tag));
        when(tagMapper.mapToTagReadDto(tag))
                .thenReturn(tagReadDto);
        assertEquals(tagReadDto, tagService.updateById(1, tagPostDto));
    }
    @Test
    public void deleteTest() {
        when(tagRepository.findById(1))
                .thenReturn(Optional.ofNullable(tag));
        tagService.delete(1);
        verify(tagRepository).delete(tag);
    }

}
