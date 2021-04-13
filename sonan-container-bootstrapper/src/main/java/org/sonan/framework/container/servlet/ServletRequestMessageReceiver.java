package org.sonan.framework.container.servlet;

import org.sonan.framework.web.protocol.Message;
import org.sonan.framework.web.protocol.RequestMessage;
import org.sonan.framework.web.protocol.Vendor;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * HttpServletRequest 에게서 message를 취득한다.
 * Created by Jaeseong on 2021/04/10
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class ServletRequestMessageReceiver {
    private static final ServletRequestMessageReceiver instance = new ServletRequestMessageReceiver();

    public static ServletRequestMessageReceiver getInstance() {
        return instance;
    }

    private ServletRequestMessageReceiver() {
    }

    public Message receive(HttpServletRequest req) throws IOException {
        if (req instanceof CachedRequestBodyHttpServletRequest) {
            return new RequestMessage(Vendor.SERVLET.name(), req.getRequestURI(), req.getMethod(), ((CachedRequestBodyHttpServletRequest) req).getCachedRequestBody());
        }
        return new RequestMessage(Vendor.SERVLET.name(), req.getRequestURI(), req.getMethod(), RequestBodyReader.read(req));
    }
}
