package ru.clevertec.ecl.integration.testcontainer;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.clevertec.ecl.integration.annotation.IT;

@IT
public class RepositoryConfiguration {

    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest");

    static {
        container.withInitScript("db/migration/V1__init.sql");
        container.start();
    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", container::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", container::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", container::getPassword);
    }
}
