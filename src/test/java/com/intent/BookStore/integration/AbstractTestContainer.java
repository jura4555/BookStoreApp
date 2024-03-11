package com.intent.BookStore.integration;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Testcontainers
public abstract class AbstractTestContainer {

    protected static final int MYSQL_PORT = 3306;
    protected static final String MYSQL_DATABASE = "BookstoreDB";
    protected static final String MYSQL_USERNAME = "root";
    protected static final String MYSQL_PASSWORD = "root";

    @Container
    protected static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:latest"))
            .withDatabaseName(MYSQL_DATABASE)
            .withUsername(MYSQL_USERNAME)
            .withPassword(MYSQL_PASSWORD)
            .withCopyFileToContainer(MountableFile.forClasspathResource("init.sql"), "/docker-entrypoint-initdb.d/init.sql");


    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

    public void setUp() {
        mySQLContainer.start();
        executeInitScript();
    }

    public void tearDown() {
        if (mySQLContainer != null) {
            mySQLContainer.stop();
        }
    }

    private static void executeInitScript() {
        try (Connection connection = DriverManager.getConnection(
                mySQLContainer.getJdbcUrl(),
                mySQLContainer.getUsername(),
                mySQLContainer.getPassword())) {
            // Load and execute init.sql script
            String initScriptContent = new String(Files.readAllBytes(
                    Paths.get(AbstractTestContainer.class.getResource("/init.sql").toURI())));
            ScriptUtils.executeSqlScript(connection, new ByteArrayResource(initScriptContent.getBytes()));
        } catch (SQLException | URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }
}