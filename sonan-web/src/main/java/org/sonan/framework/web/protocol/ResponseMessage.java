package org.sonan.framework.web.protocol;

import org.sonan.util.annotation.Nullable;

/**
 * framework 모듈에서는, container-bootstrapper에 무관히 동작하기 위해 ResponseMessage 양식을 따른다.
 * (vendor무관히 이 protocol을 기본으로 따르게 된다.)
 * immutable 하다.
 *
 * Created by Jaeseong on 2021/04/10
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class ResponseMessage implements Message {
    protected final String vendor;

    @Nullable
    protected final Object message; // ex: servlet's responseBody.

    public ResponseMessage(@Nullable String vendor, @Nullable Object message) {
        if (vendor == null)
            this.vendor = Vendor.UNKNOWN.name();
        else
            this.vendor = vendor;
        this.message = message;
    }

    public String getVendor() {
        return vendor;
    }

    @Override
    public @Nullable Object getMessage() {
        return message;
    }
}
