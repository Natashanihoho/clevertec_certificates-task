package ru.clevertec.hardziyevich.domain.certificate;

import lombok.RequiredArgsConstructor;
import ru.clevertec.hardziyevich.api.certificate.GiftCertificatePostDto;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

@RequiredArgsConstructor
public class GiftCertificateFieldUpdater implements UnaryOperator<GiftCertificate> {

    private final Optional<GiftCertificatePostDto> optionalCertificate;

    public static GiftCertificateFieldUpdater of(GiftCertificatePostDto optional) {
        return new GiftCertificateFieldUpdater(Optional.ofNullable(optional));
    }

    @Override
    public GiftCertificate apply(GiftCertificate giftCertificate) {
        fieldUpdate(GiftCertificatePostDto::getName, giftCertificate::setName);
        fieldUpdate(GiftCertificatePostDto::getDescription, giftCertificate::setDescription);
        fieldUpdate(GiftCertificatePostDto::getPrice, giftCertificate::setPrice);
        fieldUpdate(GiftCertificatePostDto::getDuration, giftCertificate::setDuration);
        return giftCertificate;
    }

    private  <T> void fieldUpdate(Function<GiftCertificatePostDto, T> converter, Consumer<T> consumer) {
        optionalCertificate
                .map(converter)
                .ifPresent(consumer);
    }

}
