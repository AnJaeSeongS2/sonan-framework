package org.sonan.framework.container.servlet.protocol;

import org.sonan.framework.container.servlet.ServletRequestMessageReceiver;
import org.sonan.framework.container.servlet.ServletResponseMessageSender;
import org.sonan.framework.web.protocol.Message;
import org.sonan.framework.web.protocol.MessageReceiver;
import org.sonan.framework.web.protocol.MessageSender;
import org.sonan.framework.web.protocol.MessageUtil;
import org.sonan.util.annotation.Nullable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Immutable ServletMessageChannel for receiving or sending Message
 *
 * Created by Jaeseong on 2021/04/11
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class ServletMessageChannel implements MessageReceiver, MessageSender {
    private final HttpServletRequest req;
    private final HttpServletResponse resp;

    public ServletMessageChannel(HttpServletRequest req, HttpServletResponse resp) {
        this.req = req;
        this.resp = resp;
    }

    @Override
    public Message receive() throws IOException {
        return ServletRequestMessageReceiver.getInstance().receive(req);
    }

    @Override
    public void send(@Nullable Message responseMessage) throws IOException {
        if (MessageUtil.isRedirectMessage(responseMessage))
            ServletResponseMessageSender.getInstance().sendRedirect(resp, responseMessage);
        else
            ServletResponseMessageSender.getInstance().send(resp, responseMessage);
    }
}
