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
    private static final String PATH_VARIABLE_PREFIX = "#";
    private static final String PATH_VARIABLE_BIND_TEMP = "#{}";

    // ex: #{id1}#{id2}
    // match1 : #{id1}
    // match2 : #{id2}
    private static final Pattern PATH_ELEMENT_PATH_VARIABLE_ON_REQUEST_MAPPING = Pattern.compile("(#\\{)([\\w]+)}");
    private static final Pattern PATH_SEPARATOR_PATTERN = Pattern.compile("/");

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
                Map.Entry<String, List<String>> currentPathElementAndPathVariables = genCurrentPathElementByMaybeUrlEncoded(pathMaybeEncoded);
                result.add(currentPathElementAndPathVariables.getKey());
                pathVariables.addAll(currentPathElementAndPathVariables.getValue());
            }
            urlMatcherBeforeEnd = urlMatcher.end();
        }
        if (urlMatcherBeforeEnd != 0) {
            String pathMaybeEncoded = url.substring(urlMatcherBeforeEnd);
            Map.Entry<String, List<String>> currentPathElementAndPathVariables = genCurrentPathElementByMaybeUrlEncoded(pathMaybeEncoded);
            result.add(currentPathElementAndPathVariables.getKey());
            pathVariables.addAll(currentPathElementAndPathVariables.getValue());
        }

        return new AbstractMap.SimpleEntry<>(result.toString(), pathVariables.toArray(new String[0]));
    }

    /**
     * encoding된 값이라면, List에 decode해서 넣고, currentPath로는 #{}으로 반환한다.
     *
     * @param pathElemMaybeEncoded
     * @return ex: #{} or #{}#{} or pathElemMaybeEncoded
     */
    private static Map.Entry<String, List<String>> genCurrentPathElementByMaybeUrlEncoded(String pathElemMaybeEncoded) {
        List<String> pathVariables = new ArrayList<>();
        if (pathElemMaybeEncoded.indexOf(PATH_VARIABLE_PREFIX) == 0) {
            // not need thread-safe
            StringBuilder currentBindTemp = new StringBuilder();
            for (String pathVariableEncoded : pathElemMaybeEncoded.split(PATH_VARIABLE_PREFIX)) {
                if (pathVariableEncoded.length() == 0)
                    continue;
                pathVariables.add(UrlUtil.decode(pathVariableEncoded));
                currentBindTemp.append(PATH_VARIABLE_BIND_TEMP);
            }
            return new AbstractMap.SimpleEntry<>(currentBindTemp.toString(), pathVariables);
        } else {
            // path static case, pathElemMaybeEncoded is normal path element.
            return new AbstractMap.SimpleEntry<>(pathElemMaybeEncoded, pathVariables);
        }
    }

    /**
     * variableName 이 있다면, list에 그것을 넣고 currentPath로는 #{}으로 반환한다.
     *
     * @param pathElemMaybeVariableName ex: #{id1} or #{id1}#{id2} or shops
     * @return ex: #{} or #{}#{} or pathElemMaybeVariableName
     */
    public static Map.Entry<String, List<String>> genCurrentPathElementAndPathVariableNames(String pathElemMaybeVariableName) {
        List<String> pathVariableNames = new ArrayList<>();
        Matcher matcher = PATH_ELEMENT_PATH_VARIABLE_ON_REQUEST_MAPPING.matcher(pathElemMaybeVariableName);
        StringBuilder currentBindTemp = new StringBuilder();
        while (matcher.find()) {
            pathVariableNames.add(matcher.group(2));
            currentBindTemp.append(PATH_VARIABLE_BIND_TEMP);
        }
        if (currentBindTemp.length() > 0) {
            return new AbstractMap.SimpleEntry<>(currentBindTemp.toString(), pathVariableNames);
        } else {
            // path static case, pathElemMaybeRequestMapping is normal path element.
            return new AbstractMap.SimpleEntry<>(pathElemMaybeVariableName, pathVariableNames);
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
                String pathElementMaybeVariableName = urlOnRequestMapping.substring(urlMatcherBeforeEnd, urlMatcher.start());
                Map.Entry<String, List<String>> currentPathElementAndPathVariableNames = genCurrentPathElementAndPathVariableNames(pathElementMaybeVariableName);
                result.add(currentPathElementAndPathVariableNames.getKey());
                pathVariables.addAll(currentPathElementAndPathVariableNames.getValue());
            }
            urlMatcherBeforeEnd = urlMatcher.end();
        }
        if (urlMatcherBeforeEnd != 0) {
            String pathElementMaybeVariableName = urlOnRequestMapping.substring(urlMatcherBeforeEnd);
            Map.Entry<String, List<String>> currentPathElementAndPathVariableNames = genCurrentPathElementAndPathVariableNames(pathElementMaybeVariableName);
            result.add(currentPathElementAndPathVariableNames.getKey());
            pathVariables.addAll(currentPathElementAndPathVariableNames.getValue());
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
