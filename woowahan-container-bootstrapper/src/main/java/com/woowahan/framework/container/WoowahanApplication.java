package com.woowahan.framework.container;

import com.woowahan.framework.container.servlet.ServletBootstrapperDefault;

/**
 * Woowahan framework 로 만든 application은 public static void run을 통해 기동한다.
 * primarySource를 기반으로 componentScan이 시작된다. basePackage는 자동지장된다.
 *
 */
public class WoowahanApplication {

    /**
     * primarySource 를 기반으로 자동 설정된다.
     */
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
