package com.kreamish.kream.common.resolver;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.function.Function;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

public abstract class CommonArgumentResolver implements HandlerMethodArgumentResolver {

    protected <T> T getOrElseDefault(String value, Function<String, T> parser, T i) {
        return getOrElseDefault(value, parser, i, false);
    }

    protected <T> T getOrElseDefault(String value, Function<String, T> parser, T i, boolean zeroAble) {
        if (value == null || (!zeroAble && "0".equals(value))) {
            return i;
        }
        return parser.apply(value);
    }

    protected <T> List<T> commaStringToList(String commaString, Function<String, T> parser) {
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
