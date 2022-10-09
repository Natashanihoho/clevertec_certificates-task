package ru.clevertec.ecl.api.certificate;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.domain.certificate.GiftCertificateService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/v1/certificates")
@RequiredArgsConstructor
@Validated
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    @PostMapping
    public ResponseEntity<GiftCertificateReadDto> create(
            @RequestBody @Valid GiftCertificatePostDto giftCertificatePostDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(giftCertificateService.create(giftCertificatePostDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificateReadDto> findById(@PathVariable @Positive Integer id) {
        return ResponseEntity.ok(giftCertificateService.findById(id));
    }

    @GetMapping("/tags/{tagNames}")
    public ResponseEntity<List<GiftCertificateReadDto>> findAllByTags(@PathVariable List<String> tagNames, Pageable pageable) {
        return ResponseEntity.ok(giftCertificateService.findAllByTagNames(tagNames, pageable));
    }

    @GetMapping
    public ResponseEntity<List<GiftCertificateReadDto>> findAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            Pageable pageable
    ) {
        return ResponseEntity.ok(giftCertificateService.findAll(name, description, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GiftCertificateReadDto> updateById(
            @PathVariable @Positive Integer id,
            @RequestBody @Valid GiftCertificatePostDto giftCertificatePostDto
    ) {
        return ResponseEntity.ok(giftCertificateService.updateById(id, giftCertificatePostDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive Integer id) {
        giftCertificateService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
