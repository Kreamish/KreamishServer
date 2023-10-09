package com.kreamish.kream.config.web;

import com.kreamish.kream.item.resolver.ItemGetItemsArgumentResolver;
import com.kreamish.kream.login.resolver.LoginMemberArgumentResolver;
import com.kreamish.kream.trade.converter.PeriodConverter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
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

    @Override
    public void addFormatters(FormatterRegistry registry) {
        WebMvcConfigurer.super.addFormatters(registry);
        registry.addConverter(new PeriodConverter());
    }
}