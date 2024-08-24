package org.demo.springcrudexample;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

@Import(PostgresConfiguration.class)
@SpringBootTest
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:tc:postgresql:16-alpine://localhost/testcontainer",
        "spring.datasource.username=test",
        "spring.datasource.password=test"
})
class SpringCrudExampleApplicationTests {

    @Test
    void contextLoads() {
        // verify startup
    }

}
