package ru.clevertec.hardziyevich.domain.certificate;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.clevertec.hardziyevich.api.certificate.GiftCertificateMapper;
import ru.clevertec.hardziyevich.api.certificate.GiftCertificatePostDto;
import ru.clevertec.hardziyevich.api.certificate.GiftCertificateReadDto;
import ru.clevertec.hardziyevich.api.exception.ApplicationException;
import ru.clevertec.hardziyevich.api.exception.ApplicationExceptionSupplier;
import ru.clevertec.hardziyevich.api.exception.Error;
import ru.clevertec.hardziyevich.api.exception.ExceptionMessage;
import ru.clevertec.hardziyevich.api.tag.TagMapper;
import ru.clevertec.hardziyevich.api.tag.TagPostDto;
import ru.clevertec.hardziyevich.domain.tag.Tag;
import ru.clevertec.hardziyevich.domain.tag.TagRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GiftCertificateService {

    private final GiftCertificateRepository giftCertificateRepository;
    private final TagRepository tagRepository;
    private final GiftCertificateMapper giftCertificateMapper;
    private final TagMapper tagMapper;

    public GiftCertificateReadDto create(GiftCertificatePostDto giftCertificatePostDto) {
        GiftCertificate giftCertificate = giftCertificateMapper.mapToGiftCertificate(giftCertificatePostDto);
        List<TagPostDto> tags = giftCertificatePostDto.getTags();
        List<Tag> existedTags = tagRepository.findByNameIn(
                tags.stream()
                        .map(TagPostDto::getName)
                        .collect(Collectors.toList())
        );
        List<Tag> savedTags = tags.stream()
                .map(tagMapper::mapToTag)
                .filter(tag -> existedTags.stream().noneMatch(existed -> existed.getName().equals(tag.getName())))
                .map(tagRepository::save)
                .collect(Collectors.toList());
        savedTags.addAll(existedTags);
        giftCertificate.setTags(savedTags);
        giftCertificateRepository.save(giftCertificate);
        return giftCertificateMapper.mapToGiftCertificateReadDto(giftCertificate);
    }

    public void delete(Integer id) {
        GiftCertificate giftCertificate = giftCertificateRepository.findById(id)
                .orElseThrow(throwException("with id = " + id));
        giftCertificateRepository.delete(giftCertificate);
    }

    public GiftCertificateReadDto findById(Integer id) {
        return giftCertificateRepository.findById(id)
                .map(giftCertificateMapper::mapToGiftCertificateReadDto)
                .orElseThrow(throwException("with id = " + id));
    }

    public List<GiftCertificateReadDto> findAllByTag(String tagName, Pageable pageable) {
        return giftCertificateRepository.findAllByTagName(tagName, pageable)
                .stream()
                .map(giftCertificateMapper::mapToGiftCertificateReadDto)
                .collect(Collectors.toList());
    }

    public List<GiftCertificateReadDto> findAll(String name, String description, Pageable pageable) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher(GiftCertificate_.NAME, ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher(GiftCertificate_.DESCRIPTION, ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
        return giftCertificateRepository.findAll(
                        Example.of(
                                GiftCertificate.builder()
                                        .name(name)
                                        .description(description)
                                        .build(),
                                exampleMatcher
                        ),
                        pageable
                ).stream()
                .map(giftCertificateMapper::mapToGiftCertificateReadDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public GiftCertificateReadDto updateById(Integer id, GiftCertificatePostDto giftCertificatePostDto) {
        return giftCertificateRepository.findById(id)
                .map(GiftCertificateFieldUpdater.of(giftCertificatePostDto))
                .map(giftCertificateMapper::mapToGiftCertificateReadDto)
                .orElseThrow(throwException("with id = " + id));
    }

    private Supplier<ApplicationException> throwException(String value) {
        return ApplicationExceptionSupplier.builder()
                .exceptionMessage(ExceptionMessage.NOT_EXIST)
                .additionalMessage(value)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .error(Error.GIFT_CERTIFICATE)
                .build();
    }
}
