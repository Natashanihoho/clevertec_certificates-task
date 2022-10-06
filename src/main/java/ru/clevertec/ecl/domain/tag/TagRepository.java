package ru.clevertec.ecl.domain.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    List<Tag> findByNameIn(List<String> names);

    @Query(value = "select tag.id, tag.name from tag\n" +
            "                                 join certificate_tag ct on tag.id = ct.tag_id\n" +
            "                                 join orders o on ct.certificate_id = o.certificate_id\n" +
            "where o.cost = (select max(orders.cost) from orders)\n" +
            "group by tag.id, tag.name\n" +
            "order by count(tag.name) desc\n" +
            "limit 1", nativeQuery = true)
    Optional<Tag> findMostPopularTag();

}
