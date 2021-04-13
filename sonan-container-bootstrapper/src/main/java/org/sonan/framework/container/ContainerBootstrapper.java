package org.sonan.framework.container;

import org.sonan.framework.container.throwable.BootingFailException;
import org.sonan.util.annotation.Nullable;

/**
 * container 를 기동시키는데 사용되는 인터페이스.
 *
 * Created by Jaeseong on 2021/03/28
 * Git Hub : https://github.com/AnJaeSeongS2
 */
@FunctionalInterface
public interface ContainerBootstrapper {

    /**
     * container가 기동된다.
     * basePackageForComponentScan 기반으로 기동된다. 지금은 Properties로 뺄 필요성을 못느꼈다.
     */
    void boot(@Nullable String basePackageForComponentScan) throws BootingFailException;
}
