package roomescape.controller.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import io.restassured.common.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.context.WebApplicationContext;
import roomescape.controller.BaseControllerUnitTest;
import roomescape.domain.Role;
import roomescape.service.UserService;
import roomescape.web.dto.user.UserRequest;
import roomescape.web.dto.user.UserResponse;

@WebMvcTest(UserController.class)
class UserControllerTest extends BaseControllerUnitTest {

    @MockitoBean
    private UserService userService;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mockMvcSetting(webApplicationContext);
    }

    @Test
    void 사용자_등록에_성공하면_201_CREATED와_정상_응답이_반환된다() {
        // given
        UserRequest request = new UserRequest("네오", "neo", "1234", Role.ROLE_USER);

        UserResponse expected = new UserResponse(1L, "neo", "네오");
        when(userService.register(any(UserRequest.class))).thenReturn(expected);

        // when
        UserResponse response = RestAssuredMockMvc.given().spec(adminSpec().log().all())
                .body(request)
                .when().post("/api/users")
                .then().log().all()
                .status(HttpStatus.CREATED)
                .extract().as(new TypeRef<>() {
                });

        // then
        assertThat(response).isEqualTo(expected);
    }
}
