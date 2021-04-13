package org.sonan.framework.web.protocol;

import org.sonan.util.annotation.Nullable;

/**
 * Message 인터페이스는 도메인간 loose-coupling 소통의 핵심 인터페이스다.
 * Message 객체는 message를 보관하고 있다.
 * 일종의 wrapper 클래스.
 *
 * message는 Object형태이다.
 * Created by Jaeseong on 2021/04/10
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public interface Message {
    @Nullable Object getMessage();
}
