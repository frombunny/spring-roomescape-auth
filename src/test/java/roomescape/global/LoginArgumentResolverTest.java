package roomescape.global;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.NativeWebRequest;
import roomescape.domain.User;
import roomescape.domain.fixture.UserFixture;
import roomescape.global.auth.LoginArgumentResolver;
import roomescape.global.auth.LoginUser;
import roomescape.repository.UserRepository;
import roomescape.repository.fake.FakeUserRepository;

@ExtendWith(MockitoExtension.class)
class LoginArgumentResolverTest extends BaseGlobalTest {
    @InjectMocks
    private LoginArgumentResolver loginArgumentResolver;

    @Mock
    private MethodParameter parameter;

    @Mock
    private MockHttpServletRequest servletRequest;

    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        this.userRepository = new FakeUserRepository();
        this.loginArgumentResolver = new LoginArgumentResolver(userRepository);
    }

    @Test
    void 파라미터가_로그인_애너테이션을_갖고_있고_파라미터_타입이_User라면_true를_반환한다() {
        // when
        when(parameter.hasParameterAnnotation(LoginUser.class)).thenReturn(true);
        when(parameter.getParameterType()).thenReturn((Class) User.class);

        // then
        assertThat(loginArgumentResolver.supportsParameter(parameter)).isTrue();
    }

    @Test
    void 파라미터가_로그인_애너테이션을_갖고_있지_않으면_false를_반환한다() {
        // when
        when(parameter.hasParameterAnnotation(LoginUser.class)).thenReturn(false);
        when(parameter.getParameterType()).thenReturn((Class) User.class);

        // then
        assertThat(loginArgumentResolver.supportsParameter(parameter)).isFalse();
    }

    @Test
    void 파라미터_타입이_User가_아니라면_false를_반환한다() {
        // when
        parameter = mock(MethodParameter.class);
        when(parameter.getParameterType()).thenReturn((Class) Boolean.class);

        // then
        assertThat(loginArgumentResolver.supportsParameter(parameter)).isFalse();
    }

    @Test
    void 세션에서_사용자_아이디를_꺼내_사용자_정보를_조회한다() throws Exception {
        // given
        User user = UserFixture.createDefaultUser();
        User saved = userRepository.save(user);

        NativeWebRequest webRequest = mock(NativeWebRequest.class);
        HttpSession session = new MockHttpSession();
        session.setAttribute("loginUserId", saved.getId());

        // when & then
        when(webRequest.getNativeRequest(any())).thenReturn(servletRequest);
        when(servletRequest.getSession(false)).thenReturn(session);

        assertThat(loginArgumentResolver.resolveArgument(parameter, null, webRequest, null))
                .isEqualTo(saved);
    }
}
