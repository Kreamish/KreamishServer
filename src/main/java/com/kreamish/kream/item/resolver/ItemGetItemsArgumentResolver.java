package com.kreamish.kream.item.resolver;

import com.kreamish.kream.item.dto.ItemListSearchCondition;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.function.Function;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class ItemGetItemsArgumentResolver implements HandlerMethodArgumentResolver {

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
            .minPrice(getOrElseDefault(httpServletRequest.getParameter("min-price"), parseLong, null, false))
            .maxPrice(getOrElseDefault(httpServletRequest.getParameter("max-price"), parseLong, null, false))
            .sizes(commaStringToList(httpServletRequest.getParameter("sizes"), itSelf))
            .page(getOrElseDefault(httpServletRequest.getParameter("page"), parseInt, 0, true))
            .pageSize(getOrElseDefault(httpServletRequest.getParameter("page-size"), parseInt, 10, false))
            .build();
    }

    private <T> T getOrElseDefault(String value, Function<String, T> parser, T i, boolean zeroAble) {
        if (value == null || (!zeroAble && "0".equals(value))) {
            return i;
        }
        return parser.apply(value);
    }

    private <T> List<T> commaStringToList(String commaString, Function<String, T> parser) {
        if (commaString == null) {
            return null;
        }
        StringTokenizer st = new StringTokenizer(URLDecoder.decode(commaString, StandardCharsets.UTF_8), ",");
        List<T> result = new ArrayList<>();

        while (st.hasMoreTokens()) {
            result.add(parser.apply(st.nextToken()));
        }
        return result;
    }
}
