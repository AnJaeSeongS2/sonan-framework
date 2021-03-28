package com.woowahan.framework;

import com.woowahan.framework.container.Bootstrapper;
import com.woowahan.framework.container.servlet.ServletBootstrapperDefault;

public class WoowahanApplication {

    private final Class<?> primarySource;
    private Bootstrapper containerBootstrapper;

    public WoowahanApplication(Class<?> primarySource) {
        this.primarySource = primarySource;
    }

    private void run(String[] args) {
        this.containerBootstrapper = new ServletBootstrapperDefault();
        containerBootstrapper.boot();
    }

    public static void run(Class<?> primarySource, String... args) {
        new WoowahanApplication(primarySource).run(args);
    }
}
