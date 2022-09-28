package ru.clevertec.hardziyevich.domain.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.clevertec.hardziyevich.api.tag.TagMapper;
import ru.clevertec.hardziyevich.api.tag.TagReadDto;
import ru.clevertec.hardziyevich.api.tag.TagPostDto;
import ru.clevertec.hardziyevich.infrastructure.ApplicationException;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public TagReadDto create(TagPostDto tagPostDto) {
        Tag tag = tagRepository.save(tagMapper.mapToTag(tagPostDto));
        return tagMapper.mapToTagReadDto(tag);
    }

    public void delete(Integer id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ApplicationException("Tag does not exist", HttpStatus.BAD_REQUEST));
        tag.getGiftCertificates().forEach(giftCertificate -> giftCertificate.getTags().remove(tag));
        tagRepository.delete(tag);
    }

    public TagReadDto findById(Integer id) {
        return tagRepository.findById(id)
                .map(tagMapper::mapToTagReadDto)
                .orElseThrow(() -> new ApplicationException("Tag does not exist", HttpStatus.BAD_REQUEST));
    }

    @Transactional
    public TagReadDto updateById(Integer id, TagPostDto tagPostDto) {
        return tagRepository.findById(id)
                .map(tag -> {
                            Optional.ofNullable(tagPostDto.getName()).ifPresent(tag::setName);
                            return tag;
                        }
                ).map(tagMapper::mapToTagReadDto)
                .orElseThrow(() -> new ApplicationException("Tag does not exist", HttpStatus.BAD_REQUEST));
    }

}
