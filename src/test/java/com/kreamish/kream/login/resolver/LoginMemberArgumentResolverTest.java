package com.kreamish.kream.login.resolver;

import static com.kreamish.kream.login.resolver.LoginMemberArgumentResolver.AUTHORIZATION_HEADER_NAME;
import static com.kreamish.kream.login.resolver.LoginMemberArgumentResolver.AUTHORIZATION_PREFIX;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.kreamish.kream.login.LoginMemberInfo;
import java.util.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;

@ExtendWith(MockitoExtension.class)
class LoginMemberArgumentResolverTest {

    @Mock
    private MethodParameter parameter;

    @Mock
    private NativeWebRequest request;

    private LoginMemberArgumentResolver loginMemberArgumentResolver;

    @BeforeEach
    public void setup() {
        loginMemberArgumentResolver = new LoginMemberArgumentResolver();
    }

    @Test
    public void AUTHORIZATION_HEADER_IS_EXISTS_FOR_loginArgumentResolver() {
        String requestHeaderMemberId = "12341";

        //given
        when(request.getHeader(AUTHORIZATION_HEADER_NAME))
            .thenReturn(Base64.getEncoder().encodeToString(
                (AUTHORIZATION_PREFIX + requestHeaderMemberId).getBytes()
            ));

        //when
        LoginMemberInfo loginMember = (LoginMemberInfo) loginMemberArgumentResolver.resolveArgument(parameter, null,
            request, null);

        //then
        assertThat(loginMember).isNotNull()
            .extracting("memberId")
            .isNotNull()
            .isEqualTo(Long.parseLong(requestHeaderMemberId));
    }

    @Test
    public void AUTHORIZATION_HEADER_IS_NULL_FOR_loginArgumentResolver() {
        //given
        when(request.getHeader(AUTHORIZATION_HEADER_NAME)).thenReturn(null);

        //then
        assertThrows(IllegalArgumentException.class,
            () -> loginMemberArgumentResolver.resolveArgument(
                parameter, null, request, null)
        );
    }
}