package com.woowahan.framework.container.lifecycle;

@FunctionalInterface
public interface StaticLifeCycleListener {
    void listen(SimpleLifeCycle lifeCycle);
}
