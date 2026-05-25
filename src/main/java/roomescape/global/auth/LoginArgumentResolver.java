package roomescape.global.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import roomescape.domain.User;
import roomescape.global.exception.UnauthorizedException;
import roomescape.repository.UserRepository;

public class LoginArgumentResolver implements HandlerMethodArgumentResolver {
    private static final String LOGIN_USER_ID = "loginUserId";

    private final UserRepository userRepository;

    public LoginArgumentResolver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAnnotation = parameter.hasParameterAnnotation(LoginUser.class);
        boolean isUserType = User.class.isAssignableFrom(parameter.getParameterType());

        return hasAnnotation && isUserType;
    }

    @Nullable
    @Override
    public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory)
            throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpSession session = request.getSession(false);

        if (session == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        Long userId = (Long) session.getAttribute(LOGIN_USER_ID);

        if (userId == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        return userRepository.findById(userId).orElseThrow(() -> new UnauthorizedException("로그인이 필요합니다."));
    }
}
