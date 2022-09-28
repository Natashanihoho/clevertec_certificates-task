package ru.clevertec.hardziyevich.domain.certificate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.hardziyevich.api.certificate.GiftCertificateMapper;
import ru.clevertec.hardziyevich.api.certificate.GiftCertificatePostDto;
import ru.clevertec.hardziyevich.api.certificate.GiftCertificateReadDto;
import ru.clevertec.hardziyevich.api.tag.TagMapper;
import ru.clevertec.hardziyevich.api.tag.TagPostDto;
import ru.clevertec.hardziyevich.domain.tag.Tag;
import ru.clevertec.hardziyevich.domain.tag.TagRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CertificateServiceTest {
    @Mock
    private GiftCertificateRepository giftCertificateRepository;

    @Mock
    private GiftCertificateMapper giftCertificateMapper;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private TagMapper tagMapper;

    @InjectMocks
    private GiftCertificateService giftCertificateService;

    private GiftCertificatePostDto giftCertificatePostDto;
    private GiftCertificate giftCertificate;
    private GiftCertificateReadDto giftCertificateReadDto;

    @BeforeEach
    void init() {
        giftCertificatePostDto = giftCertificatePostDto();
        giftCertificate = giftCertificate();
        giftCertificateReadDto = giftCertificateReadDto();
    }

    @Test
    public void createTest() {
        when(giftCertificateMapper.mapToGiftCertificate(giftCertificatePostDto))
                .thenReturn(giftCertificate);
        when(tagRepository.findByNameIn(any())).thenReturn(Collections.emptyList());
        giftCertificateService.create(giftCertificatePostDto);
        verify(giftCertificateRepository).save(giftCertificate);
    }

    @Test
    public void findByIdTest() {
        when(giftCertificateMapper.mapToGiftCertificateReadDto(giftCertificate))
                .thenReturn(giftCertificateReadDto);
        when(giftCertificateRepository.findById(1))
                .thenReturn(Optional.of(giftCertificate));
        assertEquals(giftCertificateReadDto, giftCertificateService.findById(1));
    }

    @Test
    public void updateTest() {
        when(giftCertificateRepository.findById(1))
                .thenReturn(Optional.ofNullable(giftCertificate));
        when(giftCertificateMapper.mapToGiftCertificateReadDto(giftCertificate))
                .thenReturn(giftCertificateReadDto);
        assertEquals(giftCertificateReadDto, giftCertificateService.updateById(1, giftCertificatePostDto));
    }

    @Test
    public void deleteTest() {
        when(giftCertificateRepository.findById(1))
                .thenReturn(Optional.ofNullable(giftCertificate));
        giftCertificateService.delete(1);
        verify(giftCertificateRepository).delete(giftCertificate);
    }

    private GiftCertificatePostDto giftCertificatePostDto() {
        TagPostDto tag1 = new TagPostDto();
        tag1.setName("tag1");

        TagPostDto tag2 = new TagPostDto();
        tag2.setName("tag2");

        return GiftCertificatePostDto.builder()
                .name("test_name")
                .description("test_description")
                .price(1.0)
                .duration(1)
                .tags(Arrays.asList(tag1, tag2))
                .build();
    }

    private GiftCertificateReadDto giftCertificateReadDto() {

        return GiftCertificateReadDto.builder()
                .id(1)
                .name("test_name")
                .description("test_description")
                .price(1.0)
                .duration(1)
                .build();
    }

    private GiftCertificate giftCertificate() {
        Tag tag1 = new Tag();
        tag1.setName("tag1");

        Tag tag2 = new Tag();
        tag2.setName("tag2");

        return GiftCertificate.builder()
                .name("test_name")
                .description("test_description")
                .price(1.0)
                .duration(1)
                .tags(Arrays.asList(tag1, tag2))
                .build();
    }
}
