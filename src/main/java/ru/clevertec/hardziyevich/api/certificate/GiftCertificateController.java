package ru.clevertec.hardziyevich.api.certificate;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<GiftCertificateReadDto> create(
            @RequestBody @NotNull GiftCertificatePostDto giftCertificatePostDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(giftCertificateService.create(giftCertificatePostDto));
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<GiftCertificateReadDto> findById(@PathVariable @NotNull Integer id) {
        return ResponseEntity.ok(giftCertificateService.findById(id));
    }

    @GetMapping("/find_all_by_tag/{name}")
    public ResponseEntity<List<GiftCertificateReadDto>> findAllByTag(@PathVariable String name, Pageable pageable) {
        return ResponseEntity.ok(giftCertificateService.findAllByTag(name, pageable));
    }

    @GetMapping("/find_all")
    public ResponseEntity<List<GiftCertificateReadDto>> findAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            Pageable pageable
    ) {
        return ResponseEntity.ok(giftCertificateService.findAll(name, description, pageable));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<GiftCertificateReadDto> updateById(
            @PathVariable @NotNull Integer id, @RequestBody @NotNull GiftCertificatePostDto giftCertificatePostDto
    ) {
        return ResponseEntity.ok(giftCertificateService.updateById(id, giftCertificatePostDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull Integer id) {
        giftCertificateService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
