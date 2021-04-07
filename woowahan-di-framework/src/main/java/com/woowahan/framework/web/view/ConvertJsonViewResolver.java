package com.woowahan.framework.web.view;

import com.woowahan.framework.json.JacksonUtil;
import com.woowahan.framework.json.throwable.FailedConvertJsonException;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Jaeseong on 2021/04/08
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class ConvertJsonViewResolver extends ViewResolver {

    public ConvertJsonViewResolver(int priority) {
        super(priority);
    }

    @Override
    public String getView(Object beforeResolve, HttpServletResponse resp) throws FailedViewResolveException {
        try {
            return JacksonUtil.toJson(beforeResolve);
        } catch (FailedConvertJsonException e) {
            throw new FailedViewResolveException(e.getMessage(), e);
        }
    }
}
