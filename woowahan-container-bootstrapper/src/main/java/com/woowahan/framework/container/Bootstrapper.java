package com.woowahan.framework.container;

/**
 * container 를 기동시키는데 사용되는 인터페이스.
 *
 * Created by Jaeseong on 2021/03/28
 * Git Hub : https://github.com/AnJaeSeongS2
 */
@FunctionalInterface
public interface Bootstrapper {

    /**
     * container가 기동된다.
     */
    void boot();
}
