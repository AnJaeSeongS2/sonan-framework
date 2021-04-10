package com.woowahan.framework.container.servlet;

import com.woowahan.framework.web.protocol.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.woowahan.framework.container.servlet.Constants.DEFAULT_CONTEXT_TYPE_RES;

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
     * 현재 thread에서 send한다.
     * @param respMessage
     * @throws IOException
     */
    public void send(HttpServletResponse resp, ResponseMessage respMessage) throws IOException {
        send(resp, respMessage.getMessage());
    }

    /**
     * 현재 thread에서 send한다.
     * @param resp
     * @param message
     * @throws IOException
     */
    public void send(HttpServletResponse resp, Object message) throws IOException {
        send(resp, 200, message);
    }

    /**
     * 현재 thread에서 send한다.
     * @param resp
     * @param statusCode
     * @param message
     * @throws IOException
     */
    public void send(HttpServletResponse resp, int statusCode, Object message) throws IOException {
        send(resp, statusCode, DEFAULT_CONTEXT_TYPE_RES, message);
    }

    /**
     * 현재 thread에서 send한다.
     * @see Constants . use it for contextType setting.
     * @param resp
     * @param statusCode
     * @param contextType
     * @param message
     */
    public void send(HttpServletResponse resp, int statusCode, String contextType, Object message) throws IOException {
        resp.setStatus(statusCode);
        resp.setContentType(contextType);
        resp.getWriter().print(message);
        if (statusCode == 404) {
            if (logger.isWarnEnabled())
                logger.warn("404 not found occured.");
        }
    }
}
