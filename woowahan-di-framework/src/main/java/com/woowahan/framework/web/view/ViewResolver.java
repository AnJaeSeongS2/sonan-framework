package com.woowahan.framework.web.view;

import com.woowahan.framework.json.throwable.FailedConvertJsonException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Jaeseong on 2021/04/08
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public abstract class ViewResolver implements Comparable<ViewResolver> {
    protected int priority;
    public ViewResolver(int priority) {
        this.priority = priority;
    }

    public abstract Object resolve(Object beforeResolve, HttpServletRequest req, HttpServletResponse resp) throws FailedViewResolveException, FailedConvertJsonException, IOException;

    @Override
    public int compareTo(ViewResolver o) {
        return (priority < o.priority) ? -1 : ((priority == o.priority) ? 0 : 1);
    }
}
