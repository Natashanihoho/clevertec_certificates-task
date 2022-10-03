package ru.clevertec.hardziyevich.domain.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.clevertec.hardziyevich.api.exception.ApplicationException;
import ru.clevertec.hardziyevich.api.exception.ApplicationExceptionSupplier;
import ru.clevertec.hardziyevich.api.exception.Error;
import ru.clevertec.hardziyevich.api.exception.ExceptionMessage;
import ru.clevertec.hardziyevich.api.tag.TagMapper;
import ru.clevertec.hardziyevich.api.tag.TagPostDto;
import ru.clevertec.hardziyevich.api.tag.TagReadDto;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.function.Supplier;

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
                .orElseThrow(throwException("with id = " + id));
        tag.getGiftCertificates().forEach(giftCertificate -> giftCertificate.getTags().remove(tag));
        tagRepository.delete(tag);
    }

    public TagReadDto findById(Integer id) {
        return tagRepository.findById(id)
                .map(tagMapper::mapToTagReadDto)
                .orElseThrow(throwException("with id = " + id));
    }

    @Transactional
    public TagReadDto updateById(Integer id, TagPostDto tagPostDto) {
        return tagRepository.findById(id)
                .map(tag -> {
                            Optional.ofNullable(tagPostDto.getName()).ifPresent(tag::setName);
                            return tag;
                        }
                ).map(tagMapper::mapToTagReadDto)
                .orElseThrow(throwException("with id = " + id));
    }

    private Supplier<ApplicationException> throwException(String value) {
        return ApplicationExceptionSupplier.builder()
                .exceptionMessage(ExceptionMessage.NOT_EXIST)
                .additionalMessage(value)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .error(Error.TAG)
                .build();
    }
}
