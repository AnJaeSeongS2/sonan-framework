package org.sonan.framework.context.bean;

import org.sonan.framework.context.bean.throwable.BeanDefinitionNotRegisteredException;
import org.sonan.framework.context.bean.throwable.BeanException;

/**
 * @see BeanDefinition 을 보관하고 실시간으로 등록도 가능한 주체.
 *
 * TODO: BeanDefinitionHolder 와 BeanDefinitionRegistry 가 역할이 곂치는 것으로 보이는데, 원래의도는 BeanDefinitionHolder 는 초기 부틷도중에 로딩한 meta 관리, BeanDefinitionRegistry는 runtime에 definition을 추가가능하게끔 분리하려 했었던 의도가 있다.
 * TODO: 그러나 runtime 에 definition을 추가하는 것은 framework 안정성을 오히려 떨구는 것으로 생각해 Registry개념을 제거하고 Holder만 유지할 예정.
 * Created by Jaeseong on 2021/04/01
 * Git Hub : https://github.com/AnJaeSeongS2
 */
@FunctionalInterface
public interface BeanDefinitionRegistry {

    /**
     * @see BeanDefinition 을 관리되게 등록한다.
     *
     * @param definition
     * @throws BeanException
     */
    void register(BeanDefinition definition) throws BeanDefinitionNotRegisteredException;
}
