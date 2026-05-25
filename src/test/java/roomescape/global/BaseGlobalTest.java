package roomescape.global;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.context.WebApplicationContext;
import roomescape.repository.UserRepository;

public abstract class BaseGlobalTest {
    @MockitoBean
    private UserRepository userRepository;

    protected void mockMvcSetting(WebApplicationContext webApplicationContext) {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }
}
