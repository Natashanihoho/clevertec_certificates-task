package ru.clevertec.ecl.domain.certificate;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.api.certificate.GiftCertificateMapper;
import ru.clevertec.ecl.api.certificate.GiftCertificatePostDto;
import ru.clevertec.ecl.api.certificate.GiftCertificateReadDto;
import ru.clevertec.ecl.api.exception.EntityNotFoundException;
import ru.clevertec.ecl.api.exception.ErrorCode;
import ru.clevertec.ecl.api.tag.TagMapper;
import ru.clevertec.ecl.api.tag.TagPostDto;
import ru.clevertec.ecl.domain.tag.Tag;
import ru.clevertec.ecl.domain.tag.TagRepository;

import java.util.List;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateRepository giftCertificateRepository;
    private final TagRepository tagRepository;
    private final GiftCertificateMapper giftCertificateMapper;
    private final TagMapper tagMapper;
    private final UpdateMapper updateMapper;

    @Transactional
    @Override
    public GiftCertificateReadDto create(GiftCertificatePostDto giftCertificatePostDto) {
        GiftCertificate giftCertificate = giftCertificateMapper.mapToGiftCertificate(giftCertificatePostDto);
        giftCertificate.setTags(checkAndSaveTags(giftCertificatePostDto));
        giftCertificateRepository.save(giftCertificate);
        return giftCertificateMapper.mapToGiftCertificateReadDto(giftCertificate);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        GiftCertificate giftCertificate = giftCertificateRepository.findById(id)
                .orElseThrow(exceptionSupplier(id));
        giftCertificateRepository.delete(giftCertificate);
    }

    @Override
    public GiftCertificateReadDto findById(Integer id) {
        return giftCertificateRepository.findById(id)
                .map(giftCertificateMapper::mapToGiftCertificateReadDto)
                .orElseThrow(exceptionSupplier(id));
    }

    public List<GiftCertificateReadDto> findAllByTagNames(List<String> tags, Pageable pageable) {
        return giftCertificateRepository.findAllByTagNames(tags,pageable)
                .stream()
                .map(giftCertificateMapper::mapToGiftCertificateReadDto)
                .collect(toList());
    }

    @Override
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
                .collect(toList());
    }

    @Transactional
    @Override
    public GiftCertificateReadDto updateById(Integer id, GiftCertificatePostDto giftCertificatePostDto) {
        return giftCertificateRepository.findById(id)
                .map(giftCertificate ->
                    updateMapper.update(giftCertificate, giftCertificatePostDto))
                .map(giftCertificateMapper::mapToGiftCertificateReadDto)
                .orElseThrow(exceptionSupplier(id));
    }

    private List<Tag> checkAndSaveTags(GiftCertificatePostDto giftCertificatePostDto) {
        List<TagPostDto> tags = giftCertificatePostDto.getTags();
        List<Tag> existedTags = tagRepository.findByNameIn(
                tags.stream()
                        .map(TagPostDto::getName)
                        .collect(toList())
        );
        List<Tag> savedTags = tags.stream()
                .map(tagMapper::mapToTag)
                .filter(tag -> existedTags.stream().noneMatch(existed -> existed.getName().equals(tag.getName())))
                .map(tagRepository::save)
                .collect(toList());
        savedTags.addAll(existedTags);
        return savedTags;
    }

    private Supplier<EntityNotFoundException> exceptionSupplier(Integer id) {
        return () -> new EntityNotFoundException(
                "Gift certificate is not found with id = " + id,
                ErrorCode.CERTIFICATE
        );
    }
}
