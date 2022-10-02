package ru.clevertec.hardziyevich.api.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.hardziyevich.domain.tag.TagService;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping("/create")
    public ResponseEntity<TagReadDto> create(@RequestBody @NotNull TagPostDto tagPostDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tagService.create(tagPostDto));
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<TagReadDto> findById(@PathVariable @NotNull Integer id) {
        return ResponseEntity.ok(tagService.findById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TagReadDto> updateById(@PathVariable @NotNull Integer id, @RequestBody @NotNull TagPostDto tagPostDto) {
        return ResponseEntity.ok(tagService.updateById(id, tagPostDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull Integer id) {
        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
