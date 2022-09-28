package ru.clevertec.hardziyevich.api.flyway;

import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
@Profile("!test")
@RequiredArgsConstructor
public class FlywayMigration {

    private final Flyway flyway;

    @PreDestroy
    private void destroy() {

        flyway.clean();
    }
}
