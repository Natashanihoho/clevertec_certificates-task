package ru.clevertec.ecl.domain.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.api.exception.EntityNotFoundException;
import ru.clevertec.ecl.api.exception.ErrorCode;
import ru.clevertec.ecl.api.tag.TagMapper;
import ru.clevertec.ecl.api.tag.TagPostDto;
import ru.clevertec.ecl.api.tag.TagReadDto;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Transactional
    @Override
    public TagReadDto create(TagPostDto tagPostDto) {
        Tag tag = tagRepository.save(tagMapper.mapToTag(tagPostDto));
        return tagMapper.mapToTagReadDto(tag);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        tagRepository.findById(id)
                .map(tag -> {
                            tagRepository.delete(tag);
                            return tag;
                        }
                )
                .orElseThrow(exceptionSupplier(id));
    }

    @Override
    public TagReadDto findById(Integer id) {
        return tagRepository.findById(id)
                .map(tagMapper::mapToTagReadDto)
                .orElseThrow(exceptionSupplier(id));
    }

    @Override
    public List<TagReadDto> findAll(Pageable pageable) {
        return tagRepository.findAll(pageable)
                .stream()
                .map(tagMapper::mapToTagReadDto)
                .collect(toList());
    }

    @Override
    public TagReadDto findMostPopularTag() {
        return tagRepository.findMostPopularTag()
                .map(tagMapper::mapToTagReadDto)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    @Transactional
    public TagReadDto updateById(Integer id, TagPostDto tagPostDto) {
        return tagRepository.findById(id)
                .map(tag -> {
                            Optional.ofNullable(tagPostDto.getName()).ifPresent(tag::setName);
                            return tag;
                        }
                ).map(tagMapper::mapToTagReadDto)
                .orElseThrow(exceptionSupplier(id));
    }

    private Supplier<EntityNotFoundException> exceptionSupplier(Integer id) {
        return () -> new EntityNotFoundException(
                "Tag is not found with id = " + id,
                ErrorCode.TAG
        );
    }
}
