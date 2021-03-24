package com.woowahan.framework.core;

import org.springframework.util.AntPathMatcher;

import java.util.Map;

public class WoowahanPathMatcher {
    private AntPathMatcher antPathMatcher;

    public WoowahanPathMatcher() {
        antPathMatcher = new AntPathMatcher();
    }

    public Map<String, String> extractUriTemplateVariables(String pattern, String path) {
        return antPathMatcher.extractUriTemplateVariables(pattern, path);
    }

    public boolean match(String pattern, String path) {
        return antPathMatcher.match(pattern, path);
    }
}
