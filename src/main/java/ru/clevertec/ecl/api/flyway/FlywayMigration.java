package ru.clevertec.ecl.api.flyway;

import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
@RequiredArgsConstructor
public class FlywayMigration {

    private final Flyway flyway;

    @PreDestroy
    private void destroy() {
        flyway.clean();
    }
}
