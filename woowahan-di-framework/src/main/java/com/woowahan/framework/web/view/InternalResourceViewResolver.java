package com.woowahan.framework.web.view;

/**
 * 내부 리소스를 찾아준다.
 * Created by Jaeseong on 2021/04/08
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class InternalResourceViewResolver extends ViewResolver {
    private String prefix;
    private String suffix;

    public InternalResourceViewResolver(int priority, String prefix, String suffix) {
        super(priority);
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public String getView(Object beforeResolve) throws FailedViewResolveException {
        String resourcePath = prefix + beforeResolve + suffix;
        return resourcePath;
    }
}