package ru.clevertec.ecl.domain.order;

import ru.clevertec.ecl.domain.certificate.GiftCertificate;
import ru.clevertec.ecl.domain.user.User;

public interface GiftCertificateAndUser {
    GiftCertificate getGiftCertificate();
    User getUser();
}
