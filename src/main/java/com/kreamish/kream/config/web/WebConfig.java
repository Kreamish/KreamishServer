package com.kreamish.kream.config.web;

import com.kreamish.kream.item.resolver.ItemGetItemsArgumentResolver;
import com.kreamish.kream.login.resolver.LoginMemberArgumentResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final ItemGetItemsArgumentResolver itemGetItemsArgumentResolve;

    private final LoginMemberArgumentResolver loginMemberArgumentResolver;

    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(itemGetItemsArgumentResolve);
        argumentResolvers.add(loginMemberArgumentResolver);

    }
}