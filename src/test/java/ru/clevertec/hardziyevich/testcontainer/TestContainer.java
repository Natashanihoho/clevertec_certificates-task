package ru.clevertec.hardziyevich.testcontainer;

import org.testcontainers.containers.PostgreSQLContainer;

public class TestContainer extends PostgreSQLContainer<TestContainer> {

    private static TestContainer container = new TestContainer();

    private TestContainer() {
        super("postgres:latest");
    }

    static {
        container.withInitScript("db/init.sql");
        container.start();
    }

    public static TestContainer container() {
        return container;
    }
}
