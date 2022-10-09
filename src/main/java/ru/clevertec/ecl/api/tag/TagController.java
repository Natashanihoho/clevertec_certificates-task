package ru.clevertec.ecl.api.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.domain.tag.TagService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/v1/tags")
@RequiredArgsConstructor
@Validated
public class TagController {

    private final TagService tagService;

    @PostMapping
    public ResponseEntity<TagReadDto> create(@RequestBody @Valid TagPostDto tagPostDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tagService.create(tagPostDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagReadDto> findById(@PathVariable @Positive Integer id) {
        return ResponseEntity.ok(tagService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<TagReadDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(tagService.findAll(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagReadDto> updateById(@PathVariable @Positive Integer id, @RequestBody @Valid TagPostDto tagPostDto) {
        return ResponseEntity.ok(tagService.updateById(id, tagPostDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive Integer id) {
        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/popular")
    public ResponseEntity<TagReadDto> findMostPopularTag() {
        return ResponseEntity.ok(tagService.findMostPopularTag());
    }
}
