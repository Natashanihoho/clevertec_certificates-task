package ru.clevertec.ecl.domain.order;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import ru.clevertec.ecl.domain.certificate.GiftCertificate;
import ru.clevertec.ecl.domain.user.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;


    @ManyToOne
    @JoinColumn(name = "certificate_id")
    private GiftCertificate giftCertificate;

    private BigDecimal cost;

    @CreationTimestamp
    private LocalDateTime purchase_date;
}
