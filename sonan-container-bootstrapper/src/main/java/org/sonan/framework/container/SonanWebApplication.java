package org.sonan.framework.container;

import org.sonan.framework.container.servlet.ServletBootstrapperDefault;
import org.sonan.framework.container.throwable.BootingFailException;

/**
 * Sonan framework 로 만든 application은 public static void run을 통해 기동한다.
 * containerBootApplicationClass 를 기반으로 componentScan이 시작된다. basePackage는 자동지장된다.
 *
 */
public class SonanWebApplication {

    /**
     * containerBootApplicationClass 를 기반으로 자동 설정된다.
     * componentScan은 containerBootApplicationClass 의 package를 basePackage로 지정해 진행된다.
     */
    private final Class<?> containerBootApplicationClass;
    private ContainerBootstrapper containerBootstrapper;

    public SonanWebApplication(Class<?> containerBootApplicationClass) {
        this.containerBootApplicationClass = containerBootApplicationClass;
    }

    private void run(String[] args) throws BootingFailException {
        this.containerBootstrapper = new ServletBootstrapperDefault();
        containerBootstrapper.boot(this.containerBootApplicationClass.getPackage().getName());
    }

    public static void run(Class<?> containerBootApplicationClass, String... args) throws BootingFailException {
        new SonanWebApplication(containerBootApplicationClass).run(args);
    }
}
