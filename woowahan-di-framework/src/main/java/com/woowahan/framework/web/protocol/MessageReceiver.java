package com.woowahan.framework.web.protocol;

import java.io.IOException;

/**
 * client로부터 온 Message를 얻어낸다.
 *
 * Created by Jaeseong on 2021/04/10
 * Git Hub : https://github.com/AnJaeSeongS2
 */
@FunctionalInterface
public interface MessageReceiver {
    Message receive() throws IOException;
}
