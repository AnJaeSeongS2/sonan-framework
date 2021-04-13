package org.sonan.framework.container.lifecycle;

@FunctionalInterface
public interface StaticLifeCycleListener {
    void listen(SimpleLifeCycle lifeCycle);
}
