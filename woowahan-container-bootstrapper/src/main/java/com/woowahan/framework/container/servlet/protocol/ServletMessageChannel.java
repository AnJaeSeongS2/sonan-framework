package com.woowahan.framework.container.servlet.protocol;

import com.woowahan.framework.container.servlet.ServletRequestMessageReceiver;
import com.woowahan.framework.container.servlet.ServletResponseMessageSender;
import com.woowahan.framework.web.protocol.Message;
import com.woowahan.framework.web.protocol.MessageReceiver;
import com.woowahan.framework.web.protocol.MessageSender;
import com.woowahan.util.annotation.Nullable;

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
        ServletResponseMessageSender.getInstance().send(resp, responseMessage);
    }
}
