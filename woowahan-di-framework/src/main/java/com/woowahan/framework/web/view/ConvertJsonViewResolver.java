package com.woowahan.framework.web.view;

import com.woowahan.framework.json.JacksonUtil;
import com.woowahan.framework.json.throwable.FailedConvertJsonException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Jaeseong on 2021/04/08
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class ConvertJsonViewResolver extends ViewResolver {

    public ConvertJsonViewResolver(int priority) {
        super(priority);
    }

    @Override
    public void resolveToClient(Object beforeResolve, HttpServletRequest req, HttpServletResponse resp) throws FailedViewResolveException {
        try {
            // light job. so, work on this thread.
            resp.getWriter().write(JacksonUtil.getInstance().toJson(beforeResolve));
        } catch (FailedConvertJsonException | IOException e) {
            throw new FailedViewResolveException(e.getMessage(), e);
        }
    }
}
