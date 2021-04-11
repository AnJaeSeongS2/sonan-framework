package com.woowahan.framework.web.protocol;

import com.woowahan.util.annotation.Nullable;

import java.io.IOException;

/**
 * Message를 client에게 보낸다.
 * Created by Jaeseong on 2021/04/10
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public interface MessageSender {
    void send(@Nullable Message responseMessage) throws IOException;
}
