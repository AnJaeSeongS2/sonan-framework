package org.sonan.framework.context;

import org.sonan.framework.context.bean.BeanDefinitionRegistry;
import org.sonan.framework.context.bean.BeanHolder;
import org.sonan.framework.context.bean.throwable.BeanCreationFailedException;
import org.sonan.util.annotation.Nullable;

/**
 * ApplicationContext는 vendor에 상관없이 vendor로 지정된 context에서 계층을 이루며 저장된다.
 *
 * Created by Jaeseong on 2021/03/31
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public abstract class ApplicationContext implements BeanHolder, BeanDefinitionRegistry {

    /**
     * vendor context에 저장될 때 사용되는 key
     */
    public static final String ROOT_APPLICATION_CONTEXT_ATTRIBUTE_KEY = ApplicationContext.class.getName() + ".ROOT";

    /**
     * Return the parent context, or {@code null} if there is no parent
     * and this is the root of the context hierarchy.
     * @return the parent context, or {@code null} if there is no parent
     */
    @Nullable
    public abstract ApplicationContext getParent();

    /**
     * Return the parent context, or {@code null} if there is no parent
     * and this is the root of the context hierarchy.
     * @return the parent context, or {@code null} if there is no parent
     */
    public abstract ApplicationContext getRoot();

    @Override
    public String toString() {
        String current = getClass().getName() + "@" + Integer.toHexString(hashCode());
        if (getParent() == null) {
            return current;
        } else {
            return getParent().toString() + " > " + current;
        }
    }

    /**
     * context내의 관리중인 instance를 초기화 한다.
     */
    public abstract void refreshInstances() throws BeanCreationFailedException;
}
