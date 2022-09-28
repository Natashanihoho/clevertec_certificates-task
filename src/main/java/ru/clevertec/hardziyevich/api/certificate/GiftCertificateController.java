package ru.clevertec.hardziyevich.api.certificate;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.hardziyevich.domain.certificate.GiftCertificateService;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/certificate")
@RequiredArgsConstructor
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    @PostMapping("/create")
    public ResponseEntity<GiftCertificateReadDto> create(@RequestBody @NotNull GiftCertificatePostDto giftCertificatePostDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(giftCertificateService.create(giftCertificatePostDto));
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<GiftCertificateReadDto> findById(@PathVariable @NotNull Integer id) {
        return ResponseEntity.ok(giftCertificateService.findById(id));
    }

    @GetMapping("/find_all_by_tag/{name}")
    public ResponseEntity<List<GiftCertificateReadDto>> findAllByTag(@PathVariable @NotNull String name) {
        return ResponseEntity.ok(giftCertificateService.findAllByTag(name));
    }

    @GetMapping("/find_by_name/{name}")
    public ResponseEntity<GiftCertificateReadDto> findByName(@PathVariable @NotNull String name) {
        return ResponseEntity.ok(giftCertificateService.findByName(name));
    }

    @GetMapping("/find_by_description/{text}")
    public ResponseEntity<GiftCertificateReadDto> findByDescriptionPart(@PathVariable @NotNull String text) {
        return ResponseEntity.ok(giftCertificateService.findByDescriptionPart(text));
    }

    @GetMapping("/find_all/sort_by/{field}")
    public ResponseEntity<List<GiftCertificateReadDto>> findAllAndSort(@PathVariable @NotNull String field) {
        return ResponseEntity.ok(giftCertificateService.findAllAndSort(field));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<GiftCertificateReadDto> updateById(@PathVariable @NotNull Integer id, @RequestBody @NotNull GiftCertificatePostDto giftCertificatePostDto) {
        return ResponseEntity.ok(giftCertificateService.updateById(id, giftCertificatePostDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull Integer id) {
        giftCertificateService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
