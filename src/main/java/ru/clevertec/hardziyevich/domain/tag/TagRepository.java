package ru.clevertec.hardziyevich.domain.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Integer>, JpaSpecificationExecutor<Tag> {

    List<Tag> findByNameIn(List<String> names);

    Optional<Tag> findByName(String name);

}
