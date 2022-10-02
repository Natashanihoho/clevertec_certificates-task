package ru.clevertec.hardziyevich.domain.certificate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.clevertec.hardziyevich.api.certificate.GiftCertificateMapper;
import ru.clevertec.hardziyevich.api.certificate.GiftCertificatePostDto;
import ru.clevertec.hardziyevich.api.certificate.GiftCertificateReadDto;
import ru.clevertec.hardziyevich.api.tag.TagMapper;
import ru.clevertec.hardziyevich.domain.tag.TagRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        giftCertificatePostDto = CertificateFactory.giftCertificatePostDto();
        giftCertificate = CertificateFactory.giftCertificate();
        giftCertificateReadDto = CertificateFactory.giftCertificateReadDto();
    }

    @Test
    public void createTest() {
        when(giftCertificateMapper.mapToGiftCertificate(giftCertificatePostDto))
                .thenReturn(giftCertificate);
        when(tagRepository.findByNameIn(any()))
                .thenReturn(Collections.emptyList());
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

    @Test
    public void findAllTest() {
        PageRequest pageable = PageRequest.of(0, 2);
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        giftCertificates.add(giftCertificate);
        giftCertificates.add(giftCertificate);
        when(giftCertificateRepository.findAll(any(), eq(pageable)))
                .thenReturn(new PageImpl<>(giftCertificates));
        when(giftCertificateMapper.mapToGiftCertificateReadDto(giftCertificate))
                .thenReturn(giftCertificateReadDto, giftCertificateReadDto);
        List<GiftCertificateReadDto> allAndSort = giftCertificateService.findAll(null, null, pageable);
        assertEquals(2, allAndSort.size());
        assertEquals(giftCertificateReadDto, allAndSort.get(0));
    }

}
