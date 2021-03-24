package com.woowahan.framework;

import com.woowahan.framework.web.servlet.SimpleServletWebServerApplicationContext;

public class WoowahanApplication {

    private final Class<?> primarySource;
    private SimpleServletWebServerApplicationContext context;

    public WoowahanApplication(Class<?> primarySource) {
        this.primarySource = primarySource;
    }

    private void run(String[] args) {
        this.context = new SimpleServletWebServerApplicationContext();
        context.refresh();
    }

    public static void run(Class<?> primarySource, String... args) {
        new WoowahanApplication(primarySource).run(args);
    }
}
