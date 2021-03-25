package com.woowahan.framework.web;

@FunctionalInterface
public interface StaticLifeCycleListener {
    void listen(SimpleLifeCycle lifeCycle);
}
