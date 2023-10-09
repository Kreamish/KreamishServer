package com.kreamish.kream.login.resolver;

import static com.kreamish.kream.login.LoginMemberInfo.NOT_LOGGED_IN_MEMBER_ID;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

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

    public static final String AUTHORIZATION_PREFIX = "Basic ";
    public static final String KREAMISH_PASSWORD = "kreamish";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(Login.class) != null
            && parameter.getParameterType().equals(LoginMemberInfo.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        final boolean required = parameter.getParameterAnnotation(Login.class).required();
        try {
            return getLoginMemberInfoOrThrow(webRequest);
        } catch (IllegalArgumentException e) {
            if (required) {
                throw e;
            }
            return new LoginMemberInfo(NOT_LOGGED_IN_MEMBER_ID);
        }
    }

    private LoginMemberInfo getLoginMemberInfoOrThrow(NativeWebRequest webRequest) {
        // Basic ${base64(memberId:kreamish)}
        String authString = webRequest.getHeader(AUTHORIZATION);
        if (StringUtils.isBlank(authString)) {
            throw new IllegalArgumentException("Authorization header 가 필요합니다.");
        }

        if (!authString.startsWith(AUTHORIZATION_PREFIX)) {
            throw new IllegalArgumentException("Authorization prefix 가 올바르지 않습니다");
        }

        // ${base64(memberId:kreamish)}
        String encodedStr = authString.substring(AUTHORIZATION_PREFIX.length());

        // memberId:kreamish
        String memberIdAndPassword = new String(
            Base64.getDecoder().decode(encodedStr.getBytes()));

        // memberId
        String memberId = memberIdAndPassword.split(":")[0];

        if (isNotLongParsable(memberId)) {
            throw new IllegalArgumentException("memberId는 1이상의 Long 타입이어야 합니다.");
        }
        return new LoginMemberInfo(Long.parseLong(memberId));
    }


    private Boolean isNotLongParsable(String string) {
        try {
            long value = Long.parseLong(string);
            return value <= 0;
        } catch (NumberFormatException e) {
            return true;
        }
    }
}
