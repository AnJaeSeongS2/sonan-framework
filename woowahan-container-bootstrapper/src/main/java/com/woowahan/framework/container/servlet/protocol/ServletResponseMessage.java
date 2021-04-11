package com.woowahan.framework.container.servlet.protocol;

import com.woowahan.framework.web.protocol.ResponseMessage;
import com.woowahan.util.annotation.Nullable;

import static com.woowahan.framework.container.servlet.Constants.DEFAULT_CONTENT_TYPE_RES;

/**
 * status, content-type 을 지정가능한, ResponseMessage.
 * immutable
 *
 * Created by Jaeseong on 2021/04/11
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class ServletResponseMessage extends ResponseMessage {
    protected final int status;
    protected final String contentType;

    /**
     * @param vendor : default
     * @param message : default null
     * @param status : default 404
     * @param contentType : default DEFAULT_CONTENT_TYPE_RES's value.
     */
    public ServletResponseMessage(@Nullable String vendor, @Nullable Object message, @Nullable Integer status, @Nullable String contentType) {
        super(vendor, message);
        if (status == null)
            this.status = 200;
        else
            this.status = status;
        if (contentType == null)
            this.contentType = DEFAULT_CONTENT_TYPE_RES;
        else
            this.contentType = contentType;
    }
}
