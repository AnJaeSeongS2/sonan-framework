package com.woowahan.framework.web.util;

import com.woowahan.logback.support.Markers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jaeseong on 2021/04/07
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class UrlUtil {
    public static final String URL_CHAR_SET = "UTF-8";
    public static final String PATH_SEPARATOR = "/";
    private static final Logger logger = LoggerFactory.getLogger(UrlUtil.class);
    private static final Pattern PATH_SEPARATOR_PATTERN = Pattern.compile("/");
    private static final String PATH_VARIABLE_PREFIX = "#";
    private static final String PATH_VARIABLE_BIND_TEMP = "#{}";

    /**
     * routePath를 반환한다.
     * @param url example: /a/#id1_URL_ENCODED#id2_URL_ENCODED/c/d/#id3_URL_ENCODED
     * @return example: /a/#{}#{}/c/d/#{}
     */
    public static String genRoutePathFromUrl(String url) {
        return genRoutePathAndPathVariablesFromUrl(url).getKey();
    }


    /**
     * routePath와 pathVariables를 반환한다.
     * @param url example: /a/#id1_URL_ENCODED#id2_URL_ENCODED/c/d/#id3_URL_ENCODED
     * @return example: { /a/#{}#{}/c/d/#{} , (decoded) [id1value, id2value, id3value]}
     */
    public static Map.Entry<String, String[]> genRoutePathAndPathVariablesFromUrl(String url) {
        List<String> pathVariables = new ArrayList<>();
        Matcher urlMatcher = PATH_SEPARATOR_PATTERN.matcher(url);

        StringJoiner result = new StringJoiner(PATH_SEPARATOR, PATH_SEPARATOR, "");
        int urlMatcherBeforeEnd = 0;
        while (urlMatcher.find()) {
            if (urlMatcherBeforeEnd != 0) {
                String pathMaybeEncoded = url.substring(urlMatcherBeforeEnd, urlMatcher.start());
                result.add(genCurrentPath(pathMaybeEncoded, pathVariables));
            }
            urlMatcherBeforeEnd = urlMatcher.end();
        }
        if (urlMatcherBeforeEnd != 0) {
            String pathMaybeEncoded = url.substring(urlMatcherBeforeEnd);
            result.add(genCurrentPath(pathMaybeEncoded, pathVariables));
        }

        return new AbstractMap.SimpleEntry<>(result.toString(), pathVariables.toArray(new String[0]));
    }

    /**
     * encoding된 값이라면 decode해서 반환, pathVariable이라면 appendablePathVariables에도 추가해둔다.
     *
     * @TODO REFACTORING: param으로 넘겨준 자료구조를 CUD하는 행위는 좋은 코딩습관이 아니다. 그러나, 지금은 private method로 확실히 동작을 안 상태로 사용할 것.
     * @param pathMaybeEncoded
     * @param appendablePathVariables param으로 넘겨준 값을 변조하는 행위는 좋지않으나, private method로 확실히 동작을 안 상태로 사용할 것.
     * @return
     */
    private static String genCurrentPath(String pathMaybeEncoded, List<String> appendablePathVariables) {
        if (pathMaybeEncoded.indexOf(PATH_VARIABLE_PREFIX) == 0) {
            // no need thread-safe
            StringBuilder currentBindTemp = new StringBuilder();
            for (String pathVariableEncoded : pathMaybeEncoded.split(PATH_VARIABLE_PREFIX)) {
                if (pathVariableEncoded.length() == 0)
                    continue;
                appendablePathVariables.add(UrlUtil.decode(pathVariableEncoded));
                currentBindTemp.append(PATH_VARIABLE_BIND_TEMP);
            }
            return currentBindTemp.toString();
        } else {
            // path static case, pathMaybeEncoded is normal path.
            return pathMaybeEncoded;
        }
    }

    /**
     * pathVariable이라면 appendablePathVariables에도 추가해둔다.
     *
     *
     * @TODO REFACTORING: param으로 넘겨준 자료구조를 CUD하는 행위는 좋은 코딩습관이 아니다. 그러나, 지금은 private method로 확실히 동작을 안 상태로 사용할 것.
     * @param pathMaybeVariableName
     * @param appendablePathVariables param으로 넘겨준 값을 변조하는 행위는 좋지않으나, private method로 확실히 동작을 안 상태로 사용할 것.
     * @return
     */
    private static String genCurrentPathMaybeVariableName(String pathMaybeVariableName, List<String> appendablePathVariables) {
        if (pathMaybeVariableName.indexOf(PATH_VARIABLE_PREFIX) == 0) {
            // no need thread-safe
            StringBuilder currentBindTemp = new StringBuilder();
            for (String elementMaybePathVariableName : pathMaybeVariableName.split(PATH_VARIABLE_PREFIX)) {
                if (elementMaybePathVariableName.length() == 0)
                    continue;
                appendablePathVariables.add(elementMaybePathVariableName.substring(1, elementMaybePathVariableName.length() - 1));
                currentBindTemp.append(PATH_VARIABLE_BIND_TEMP);
            }
            return currentBindTemp.toString();
        } else {
            // path static case, pathMaybeVariable is normal path.
            return pathMaybeVariableName;
        }
    }

    /**
     * routePath를 반환한다.
     * @param urlOnRequestMapping example: /a/#{id1}#{id2}/c/d/#{id3}
     * @return example: /a/#{}#{}/c/d/#{}
     */
    public static String genRoutePathFromUrlOnRequestMapping(String urlOnRequestMapping) {
        return genRoutePathAndPathVariableNamesFromUrlOnRequestMapping(urlOnRequestMapping).getKey();
    }

    /**
     * routePath와 pathVariableNames를 반환한다.
     *
     * @param urlOnRequestMapping example: /a/#{id1}#{id2}/c/d/#{id3}
     * @return example: { /a/#{}#{}/c/d/#{} , [id1, id2, id3]}
     */
    public static Map.Entry<String, String[]> genRoutePathAndPathVariableNamesFromUrlOnRequestMapping(String urlOnRequestMapping) {
        List<String> pathVariables = new ArrayList<>();
        Matcher urlMatcher = PATH_SEPARATOR_PATTERN.matcher(urlOnRequestMapping);

        StringJoiner result = new StringJoiner(PATH_SEPARATOR, PATH_SEPARATOR, "");
        int urlMatcherBeforeEnd = 0;
        while (urlMatcher.find()) {
            if (urlMatcherBeforeEnd != 0) {
                String pathMaybeVariableName = urlOnRequestMapping.substring(urlMatcherBeforeEnd, urlMatcher.start());
                result.add(genCurrentPathMaybeVariableName(pathMaybeVariableName, pathVariables));
            }
            urlMatcherBeforeEnd = urlMatcher.end();
        }
        if (urlMatcherBeforeEnd != 0) {
            String pathMaybeVariableName = urlOnRequestMapping.substring(urlMatcherBeforeEnd);
            result.add(genCurrentPathMaybeVariableName(pathMaybeVariableName, pathVariables));
        }

        return new AbstractMap.SimpleEntry<>(result.toString(), pathVariables.toArray(new String[0]));
    }

    public static String encode(String src) {
        try {
            return URLEncoder.encode(src, URL_CHAR_SET);
        } catch (UnsupportedEncodingException e) {
            if (logger.isDebugEnabled())
                logger.debug(Markers.MESSAGE.get(), String.format("url cannot be encoded. so, return original url value. this url : ", src));
            return src;
        }
    }

    public static String decode(String src) {
        try {
            return URLDecoder.decode(src, URL_CHAR_SET);
        } catch (UnsupportedEncodingException e) {
            if (logger.isDebugEnabled())
                logger.debug(Markers.MESSAGE.get(), String.format("url cannot be decoded. so, return original url value. this url(encoded) : ", src));
            return src;
        }
    }
}
