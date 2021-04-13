package org.sonan.framework.container.servlet;

import org.sonan.framework.container.servlet.protocol.ServletResponseMessage;
import org.sonan.framework.web.protocol.Message;
import org.sonan.framework.web.protocol.MessageUtil;
import org.sonan.logback.support.Markers;
import org.sonan.util.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

import static org.sonan.framework.container.servlet.Constants.DEFAULT_CONTENT_TYPE_RES;

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
     * @param message : Message Interface's object. (ex: ResponseMessage, ServletResponseMessage)
     * @throws IOException
     */
    public void send(HttpServletResponse resp, @Nullable Message message) throws IOException {
        if (message == null) {
            // 404 Not Found.
            send(resp, 404, null);
        }
        send(resp, null, message);
    }

    /**
     * 현재 thread에서 send한다.
     * @param resp
     * @param status
     * @param message
     * @throws IOException
     */
    public void send(HttpServletResponse resp, @Nullable Integer status, @Nullable Message message) throws IOException {
        send(resp, status, null, message);
    }

    /**
     * 현재 thread에서 send한다.
     * @see Constants . use it for contextType setting.
     * @param resp
     * @param status
     * @param contentType
     * @param message
     */
    public void send(HttpServletResponse resp, @Nullable Integer status, @Nullable String contentType, @Nullable Message message) throws IOException {
        Integer curStatus = null;
        String curContentType = null;
        if (message instanceof ServletResponseMessage) {
            curStatus = ((ServletResponseMessage) message).getStatus();
            curContentType = ((ServletResponseMessage) message).getContentType();
        }
        if (status != null)
            curStatus = status;
        if (contentType != null)
            curContentType = contentType;
        curStatus = curStatus == null ? 200 : curStatus;
        curContentType = curContentType == null ? DEFAULT_CONTENT_TYPE_RES : curContentType;
        resp.setStatus(curStatus);
        resp.setContentType(curContentType);
        if (message != null) {
            try (Writer writer = resp.getWriter()) {
                writer.write(String.valueOf(message.getMessage()));
            }
        }

        if (curStatus == 404) {
            if (logger.isWarnEnabled())
                logger.warn("404 not found occured.");
        }
        if (logger.isTraceEnabled(Markers.MESSAGE.get()))
            logger.trace(String.format("this message on send : %s", message));
    }

    /**
     * 현재 thread에서 send한다.  respMessage는 Message class's object.
     * @param respMessage : Message class's object.
     * @throws IOException
     */
    public void sendRedirect(HttpServletResponse resp, @Nullable Message respMessage) throws IOException {
        resp.sendRedirect(MessageUtil.getRedirectPath(respMessage));
    }
}
