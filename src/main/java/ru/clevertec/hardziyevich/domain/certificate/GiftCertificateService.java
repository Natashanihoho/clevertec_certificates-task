package ru.clevertec.hardziyevich.domain.certificate;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.clevertec.hardziyevich.api.certificate.GiftCertificatePostDto;
import ru.clevertec.hardziyevich.api.certificate.GiftCertificateReadDto;
import ru.clevertec.hardziyevich.api.certificate.GiftCertificateMapper;
import ru.clevertec.hardziyevich.api.tag.TagMapper;
import ru.clevertec.hardziyevich.api.tag.TagPostDto;
import ru.clevertec.hardziyevich.domain.tag.Tag;
import ru.clevertec.hardziyevich.domain.tag.TagRepository;
import ru.clevertec.hardziyevich.infrastructure.ApplicationException;

import javax.transaction.Transactional;
import java.util.*;
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
                .orElseThrow(() -> new ApplicationException("Gift certificate does not exist", HttpStatus.BAD_REQUEST));
        giftCertificateRepository.delete(giftCertificate);
    }

    public GiftCertificateReadDto findById(Integer id) {
        return giftCertificateRepository.findById(id)
                .map(giftCertificateMapper::mapToGiftCertificateReadDto)
                .orElseThrow(() -> new ApplicationException("Gift certificate does not exist", HttpStatus.BAD_REQUEST));
    }

    public List<GiftCertificateReadDto> findAllByTag(String tagName, Integer pageNumber) {
        Pageable pageable = PageRequest.of(Objects.isNull(pageNumber) ? 0 : pageNumber - 1, 10);
        return giftCertificateRepository.findAllByTagName(tagName, pageable)
                .stream()
                .map(giftCertificateMapper::mapToGiftCertificateReadDto)
                .collect(Collectors.toList());
    }

    public GiftCertificateReadDto findByName(String name) {
        return giftCertificateRepository.findByName(name)
                .map(giftCertificateMapper::mapToGiftCertificateReadDto)
                .orElseThrow(() -> new ApplicationException("Gift certificate does not exist", HttpStatus.BAD_REQUEST));
    }

    public GiftCertificateReadDto findByDescriptionPart(String text) {
        return giftCertificateRepository.findFirstByDescriptionContains(text)
                .map(giftCertificateMapper::mapToGiftCertificateReadDto)
                .orElseThrow(() -> new ApplicationException("Gift certificate does not exist", HttpStatus.BAD_REQUEST));
    }

    public List<GiftCertificateReadDto> findAllAndSort(String field, Integer pageNumber) {
        Pageable pageable = PageRequest.of(Objects.isNull(pageNumber) ? 0 : pageNumber - 1 ,10, Sort.by(Sort.Direction.ASC, field));
        return giftCertificateRepository.findAll(pageable)
                .stream()
                .map(giftCertificateMapper::mapToGiftCertificateReadDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public GiftCertificateReadDto updateById(Integer id, GiftCertificatePostDto giftCertificatePostDto) {
        return giftCertificateRepository.findById(id)
                .map(GiftCertificateFieldUpdater.of(giftCertificatePostDto))
                .map(giftCertificateMapper::mapToGiftCertificateReadDto)
                .orElseThrow(() -> new ApplicationException("Gift certificate does not exist", HttpStatus.BAD_REQUEST));
    }

}
