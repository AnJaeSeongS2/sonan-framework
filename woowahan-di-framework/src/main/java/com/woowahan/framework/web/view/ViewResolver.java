package com.woowahan.framework.web.view;

import com.woowahan.framework.json.throwable.FailedConvertJsonException;

/**
 * Created by Jaeseong on 2021/04/08
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public abstract class ViewResolver implements Comparable<ViewResolver> {
    protected int priority;
    public ViewResolver(int priority) {
        this.priority = priority;
    }

    public abstract String getView(Object beforeResolve) throws FailedViewResolveException, FailedConvertJsonException;

    @Override
    public int compareTo(ViewResolver o) {
        return (priority < o.priority) ? -1 : ((priority == o.priority) ? 0 : 1);
    }
}
