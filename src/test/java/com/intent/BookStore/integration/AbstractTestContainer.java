package com.intent.BookStore.integration;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public abstract class AbstractTestContainer {

    protected static final int MYSQL_PORT = 3306;
    protected static final String MYSQL_DATABASE = "BookstoreDB";
    protected static final String MYSQL_USERNAME = "root";
    protected static final String MYSQL_PASSWORD = "root";

    @Container
    protected static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:latest"))
            .withDatabaseName(MYSQL_DATABASE)
            .withUsername(MYSQL_USERNAME)
            .withPassword(MYSQL_PASSWORD);

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

    public static void setUp() {
        mySQLContainer.start();
    }

    public static void tearDown() {
        if (mySQLContainer != null) {
            mySQLContainer.stop();
        }
    }
}