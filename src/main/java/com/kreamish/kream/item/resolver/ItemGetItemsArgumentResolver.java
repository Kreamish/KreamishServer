package com.kreamish.kream.item.resolver;

import com.kreamish.kream.common.resolver.CommonArgumentResolver;
import com.kreamish.kream.item.dto.ItemListSearchCondition;
import jakarta.servlet.http.HttpServletRequest;
import java.util.function.Function;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class ItemGetItemsArgumentResolver extends CommonArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(ItemListSearchCondition.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        Function<String, String> itSelf = f -> f;
        Function<String, Long> parseLong = Long::parseLong;
        Function<String, Integer> parseInt = Integer::parseInt;
        if (httpServletRequest == null) {
            throw new IllegalArgumentException("httpServletRequest is null...");
        }

        return ItemListSearchCondition.builder()
            .categoryIds(commaStringToList(httpServletRequest.getParameter("category-ids"), parseLong))
            .brandIds(commaStringToList(httpServletRequest.getParameter("brand-ids"), parseLong))
            .collectionIds(commaStringToList(httpServletRequest.getParameter("collection-ids"), parseLong))
            .minPrice(getOrElseDefault(httpServletRequest.getParameter("min-price"), parseLong, null))
            .maxPrice(getOrElseDefault(httpServletRequest.getParameter("max-price"), parseLong, null))
            .sizes(commaStringToList(httpServletRequest.getParameter("sizes"), itSelf))
            .page(getOrElseDefault(httpServletRequest.getParameter("page"), parseInt, 0, true))
            .pageSize(getOrElseDefault(httpServletRequest.getParameter("page-size"), parseInt, 10))
            .build();
    }
}
