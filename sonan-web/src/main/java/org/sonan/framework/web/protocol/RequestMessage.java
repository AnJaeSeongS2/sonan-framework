package org.sonan.framework.web.protocol;

import org.sonan.framework.web.annotation.RequestMethod;
import org.sonan.util.annotation.Nullable;

/**
 * framework 모듈에서는, container-bootstrapper에 무관히 (vendor무관히 이 protocol을 기본으로 따르게 된다.)
 * (vendor무관히 이 protocol을 기본으로 따르게 된다.)
 * immutable 하다.
 * Created by Jaeseong on 2021/04/10
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class RequestMessage implements Message {
    @Nullable
    protected final String vendor;
    protected final String url;
    protected final String requestMethod;
    @Nullable
    protected final Object message; //ex: requestBody on Servlet.

    public RequestMessage(@Nullable String vendor, String url, @Nullable String requestMethod, @Nullable Object message) {
        if (vendor == null)
            this.vendor = Vendor.UNKNOWN.name();
        else
            this.vendor = vendor;
        this.url = url;

        requestMethodSettingFinish:
        if (requestMethod == null) {
            this.requestMethod = RequestMethod.GET.name();
        } else {
            for (RequestMethod value : RequestMethod.values()) {
                if (value.name().equals(requestMethod)) {
                    this.requestMethod = requestMethod;
                    break requestMethodSettingFinish;
                }
            }
            this.requestMethod = RequestMethod.GET.name();
        }
        this.message = message;
    }

    public @Nullable String getVendor() {
        return vendor;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public @Nullable Object getMessage() {
        return message;
    }

    public String getRequestMethod() {
        return requestMethod;
    }
}
