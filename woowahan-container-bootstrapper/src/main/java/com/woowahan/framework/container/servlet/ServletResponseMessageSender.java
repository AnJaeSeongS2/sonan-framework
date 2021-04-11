package com.woowahan.framework.container.servlet;

import com.woowahan.framework.web.protocol.Message;
import com.woowahan.logback.support.Markers;
import com.woowahan.util.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Writer;

import static com.woowahan.framework.container.servlet.Constants.DEFAULT_CONTENT_TYPE_RES;

/**
 * HttpServletResponse를 통해 원하는 message를 client에게 발송한다.
 *
 * Created by Jaeseong on 2021/04/10
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class ServletResponseMessageSender {
    private static final Logger logger = LoggerFactory.getLogger(ServletResponseMessageSender.class);
    private static final ServletResponseMessageSender instance = new ServletResponseMessageSender();
    public static ServletResponseMessageSender getInstance() {
        return instance;
    }

    private ServletResponseMessageSender() {

    }

    /**
     * 현재 thread에서 send한다.  respMessage는 Message class's object.
     * @param respMessage : Message class's object.
     * @throws IOException
     */
    public void send(HttpServletResponse resp, @Nullable Message respMessage) throws IOException {
        if (respMessage == null) {
            // 404 Not Found.
            send(resp, 404, null);
        }
        send(resp, respMessage.getMessage());
    }

    /**
     * 현재 thread에서 send한다.
     * @param resp
     * @param message
     * @throws IOException
     */
    public void send(HttpServletResponse resp, @Nullable Object message) throws IOException {
        send(resp, 200, message);
    }

    /**
     * 현재 thread에서 send한다.
     * @param resp
     * @param status
     * @param message
     * @throws IOException
     */
    public void send(HttpServletResponse resp, @Nullable Integer status, @Nullable Object message) throws IOException {
        send(resp, status == null ? 200 : status, DEFAULT_CONTENT_TYPE_RES, message);
    }

    /**
     * 현재 thread에서 send한다.
     * @see Constants . use it for contextType setting.
     * @param resp
     * @param status
     * @param contentType
     * @param message
     */
    public void send(HttpServletResponse resp, @Nullable Integer status, @Nullable String contentType, @Nullable Object message) throws IOException {
        resp.setStatus(status);
        resp.setContentType(contentType == null ? DEFAULT_CONTENT_TYPE_RES : contentType);
        if (message != null) {
            try (Writer writer = resp.getWriter()) {
                writer.write(String.valueOf(message));
            }
        }

        if (status == 404) {
            if (logger.isWarnEnabled())
                logger.warn("404 not found occured.");
        }
        if (logger.isTraceEnabled(Markers.MESSAGE.get()))
            logger.trace(String.format("this message on send : %s", message));
    }
}
