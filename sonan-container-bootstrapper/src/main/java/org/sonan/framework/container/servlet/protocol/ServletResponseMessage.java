package org.sonan.framework.container.servlet.protocol;

import org.sonan.framework.web.protocol.ResponseMessage;
import org.sonan.util.annotation.Nullable;

/**
 * status, content-type 을 지정가능한, ResponseMessage.
 * immutable
 *
 * Created by Jaeseong on 2021/04/11
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class ServletResponseMessage extends ResponseMessage {

    @Nullable
    protected final Integer status;
    @Nullable
    protected final String contentType;

    /**
     * @param vendor
     * @param message
     * @param status
     * @param contentType
     */
    public ServletResponseMessage(@Nullable String vendor, @Nullable Object message, @Nullable Integer status, @Nullable String contentType) {
        super(vendor, message);
        this.status = status;
        this.contentType = contentType;
    }

    @Nullable
    public Integer getStatus() {
        return status;
    }

    @Nullable
    public String getContentType() {
        return contentType;
    }
}
