package ru.gur.archclaim;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9094", "port=9094"})
public abstract class AbstractSpringBootTest {

    @LocalServerPort
    protected int port;

    @DynamicPropertySource
    static void dbProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url",
                () -> "jdbc:tc:postgresql:12.9-alpine:///spring_boot_testcontainers");
        registry.add("spring.test.database.replace", () -> "none");
        registry.add("spring.datasource.driverClassName", () -> "org.testcontainers.jdbc.ContainerDatabaseDriver");
        registry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.PostgreSQLDialect");

        registry.add("kafka.bootstrapAddress", () -> "localhost:9094");
    }
}
