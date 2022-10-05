package ru.clevertec.ecl.domain.user;

import lombok.*;
import ru.clevertec.ecl.domain.order.Order;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstname;

    private String lastname;

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

}
