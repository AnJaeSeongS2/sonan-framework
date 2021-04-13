package org.sonan.framework.web.protocol;

import org.sonan.util.annotation.Nullable;

import java.io.IOException;

/**
 * Message를 client에게 보낸다.
 * Created by Jaeseong on 2021/04/10
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public interface MessageSender {
    /**
     * Message 있는 데이터 기반으로 응답을 client에게 제공한다.
     * @param responseMessage
     * @throws IOException
     */
    void send(@Nullable Message responseMessage) throws IOException;
}
