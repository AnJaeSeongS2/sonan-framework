package org.sonan.framework.web.protocol;

import java.io.IOException;

/**
 * client로부터 온 Message를 얻어낸다.
 *
 * Created by Jaeseong on 2021/04/10
 * Git Hub : https://github.com/AnJaeSeongS2
 */
@FunctionalInterface
public interface MessageReceiver {
    /**
     * client에게서 메시지(데이터)를 받아 Message로 반환한다.
     * @return
     * @throws IOException
     */
    Message receive() throws IOException;
}
