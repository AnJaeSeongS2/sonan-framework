package com.woowahan.framework.core;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class WoowahanLocalVariableTableParameterNameDiscoverer {
    private LocalVariableTableParameterNameDiscoverer delegate = new LocalVariableTableParameterNameDiscoverer();

    public String[] getParameterNames(Method method) {
        return delegate.getParameterNames(method);
    }

    public String[] getParameterNames(Constructor<?> ctor) {
        return delegate.getParameterNames(ctor);
    }
}
