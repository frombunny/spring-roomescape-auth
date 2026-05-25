package roomescape.controller;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecBuilder;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.context.WebApplicationContext;
import roomescape.repository.UserRepository;
import roomescape.repository.fake.FakeUserRepository;

public abstract class BaseControllerUnitTest {
    @MockitoBean
    private UserRepository userRepository;

    protected void mockMvcSetting(WebApplicationContext webApplicationContext) {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    protected MockMvcRequestSpecification adminSpec() {
        return new MockMvcRequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("role", "ADMIN")
                .build();
    }

    protected MockMvcRequestSpecification defaultSpec() {
        return new MockMvcRequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
    }
}
