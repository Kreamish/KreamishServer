package com.kreamish.kream.login.resolver;

import com.kreamish.kream.common.resolver.CommonArgumentResolver;
import com.kreamish.kream.login.Login;
import com.kreamish.kream.login.LoginMemberInfo;
import io.micrometer.common.util.StringUtils;
import java.util.Base64;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginMemberArgumentResolver extends CommonArgumentResolver {

    public static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    public static final String AUTHORIZATION_PREFIX = "basic ";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(Login.class) != null
            && parameter.getParameterType().equals(LoginMemberInfo.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String authString = webRequest.getHeader(AUTHORIZATION_HEADER_NAME);
        if (StringUtils.isBlank(authString)) {
            throw new IllegalArgumentException("Authorization header 가 필요합니다.");
        }

        String decodeValue = new String(Base64.getDecoder().decode(
            authString)
        );

        // Authorization: basic memberId
        String[] split = decodeValue.split(AUTHORIZATION_PREFIX);
        if (split.length <= 1) {
            throw new IllegalArgumentException("잘못된 토큰입니다.");
        }

        if (isLongParsable(split[1])) {
            throw new IllegalArgumentException("memberId는 1이상의 Long 타입이어야 합니다.");
        }
        return new LoginMemberInfo(Long.parseLong(split[1]));
    }

    private Boolean isLongParsable(String string) {
        try {
            long value = Long.parseLong(string);
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
