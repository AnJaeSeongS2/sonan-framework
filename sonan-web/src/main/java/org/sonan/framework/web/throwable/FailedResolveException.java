package org.sonan.framework.web.throwable;

import org.sonan.framework.web.model.ModelResolver;

/**
 * Message에 기반한 resolve 작업이 실패했다.
 *
 * @see ModelResolver
 * @see org.sonan.framework.web.view.ViewResolver
 * Created by Jaeseong on 2021/04/11
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class FailedResolveException extends Exception {
    public FailedResolveException(String message) {
        super(message);
    }

    public FailedResolveException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedResolveException(Throwable cause) {
        super(cause);
    }
}
