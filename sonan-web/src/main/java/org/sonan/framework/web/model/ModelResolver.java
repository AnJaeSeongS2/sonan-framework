package org.sonan.framework.web.model;

import org.sonan.framework.web.protocol.MessageResolver;
import org.sonan.framework.web.protocol.ResponseMessage;
import org.sonan.framework.web.protocol.MessageSender;
import org.sonan.framework.web.throwable.FailedResolveException;
import org.sonan.util.collection.ClassWithPriority;

import java.io.IOException;

/**
 * ViewResolver 의 기본구현 상태.
 *
 * View를 해결하고 해결된 ResolvedMessage를 sender에 전달한다.
 * beforeReolve 인 message를 resolve하고 ResponseMessageSender에 send한다.
 * Created by Jaeseong on 2021/04/08
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public abstract class ModelResolver extends ClassWithPriority implements MessageResolver {

    public ModelResolver(int priority) {
        super(priority);
    }

    /**
     * [no overwrite this method. if you make extension.]
     *
     * @param messageBeforeResolve
     * @param sender
     * @throws FailedResolveException
     */
    public void resolve(ResponseMessage messageBeforeResolve, MessageSender sender) throws FailedResolveException, IOException {
        sender.send(resolve(messageBeforeResolve));
    }
}
