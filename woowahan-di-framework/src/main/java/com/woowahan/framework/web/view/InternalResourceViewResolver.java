package com.woowahan.framework.web.view;

import javax.servlet.http.HttpServletResponse;

/**
 * 내부 리소스를 찾아준다.
 * Created by Jaeseong on 2021/04/08
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class InternalResourceViewResolver extends ViewResolver {
    private String prefix;
    private String suffix;
    private String contentType;

    public InternalResourceViewResolver(int priority, String prefix, String suffix, String contentType) {
        super(priority);
        this.prefix = prefix;
        this.suffix = suffix;
        this.contentType = contentType;
    }

    public String getView(Object beforeResolve, HttpServletResponse resp) throws FailedViewResolveException {
        String resourcePath = prefix + beforeResolve + suffix;
        resp.setContentType(contentType);
        return resourcePath;
    }
}