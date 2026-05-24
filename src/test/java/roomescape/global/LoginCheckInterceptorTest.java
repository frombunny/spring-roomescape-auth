package roomescape.global;

import static org.hamcrest.Matchers.containsString;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(DummyController.class)
class LoginCheckInterceptorTest {

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    @Test
    void 로그인이_필요한_API로_요청_시_로그인_정보가_없으면_예외가_발생한다() {
        RestAssuredMockMvc.given().log().all()
                .contentType(MediaType.APPLICATION_JSON)
                .when().get("/api/user")
                .then().log().all()
                .status(HttpStatus.UNAUTHORIZED)
                .body(containsString("로그인이 필요합니다."));
    }

    @Test
    void 로그인이_필요한_API로_요청_시_로그인_정보가_있으면_200_OK를_응답한다() {
        RestAssuredMockMvc.given().log().all()
                .contentType(MediaType.APPLICATION_JSON)
                .sessionAttr("loginUserId", "1")
                .when().get("/api/user")
                .then().log().all()
                .status(HttpStatus.OK);
    }
}
