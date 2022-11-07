package ru.clevertec.ecl.domain.order;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("select g as giftCertificate,u as user from User as u join GiftCertificate as g on u.id = :userId and g.id"
            + " = :giftCertificateId")
    Optional<GiftCertificateAndUser> findGiftCertificateAndUser(Integer userId, Integer giftCertificateId);

    @Query(value = "select orders_id_seq.last_value from orders_id_seq", nativeQuery = true)
    Integer sequence();

    @Query(value = "select setval('orders_id_seq', :sequence)",nativeQuery = true)
    Integer setSequence(Integer sequence);

    List<Order> findAllByIdGreaterThanAndIdLessThanEqual(Integer mix, Integer max);
}
