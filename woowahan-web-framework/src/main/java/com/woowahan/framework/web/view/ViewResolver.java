package com.woowahan.framework.web.view;

import com.woowahan.framework.context.annotation.Autowired;
import com.woowahan.framework.context.annotation.BeanVariable;
import com.woowahan.framework.web.protocol.Message;
import com.woowahan.framework.web.protocol.MessageResolver;
import com.woowahan.framework.web.protocol.MessageSender;
import com.woowahan.framework.web.throwable.FailedResolveException;
import com.woowahan.util.collection.ClassWithPriority;

import java.io.IOException;

/**
 * ViewResolver 의 기본구현 상태.
 *
 * View를 해결하고 해결된 ResolvedMessage를 sender에 전달한다.
 * beforeReolve 인 message를 resolve하고 ResponseMessageSender에 send한다.
 * Created by Jaeseong on 2021/04/08
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public abstract class ViewResolver extends ClassWithPriority implements MessageResolver {


    public ViewResolver(int priority) {
        super(priority);
    }

    /**
     * [no overwrite this method. if you make extension.]
     *
     * @param messageBeforeResolve
     * @param sender
     * @throws FailedResolveException
     */
    @Autowired
    public void resolve(Message messageBeforeResolve, @BeanVariable MessageSender sender) throws FailedResolveException, IOException {
        sender.send(resolve(messageBeforeResolve));
    }
}
