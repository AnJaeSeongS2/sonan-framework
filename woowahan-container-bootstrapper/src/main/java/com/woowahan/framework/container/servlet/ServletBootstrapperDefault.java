package com.woowahan.framework.container.servlet;

import com.woowahan.framework.container.Bootstrapper;
import com.woowahan.framework.container.lifecycle.SimpleLifeCycle;
import com.woowahan.framework.container.lifecycle.StaticLifeCycleEventBus;
import com.woowahan.framework.container.server.TomcatWebServer;
import com.woowahan.framework.container.server.TomcatWebServerFactory;
import com.woowahan.framework.container.server.filter.CharacterEncodingFilter;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class ServletBootstrapperDefault implements Bootstrapper {
    public static final String DISPATCHER_SERVLET_NAME = "dispatcherServlet";

    private TomcatWebServer webServer;

    public void boot() {
        createWebServer();
        registerShutdownHandler();
        start();
        StaticLifeCycleEventBus.send(SimpleLifeCycle.AFTER_START_EVENT);
    }


    /**
     * TODO: addShutdownHook 이 항상 보장되지 않을탠데, 검토바람.
     */
    private void registerShutdownHandler() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> webServer.stop()));
    }

    private void start() {
        this.webServer.start();
    }

    private void createWebServer() {
        TomcatWebServerFactory tomcatWebServerFactory = new TomcatWebServerFactory();
        this.webServer = tomcatWebServerFactory.getWebServer(this::selfInitialize);
    }

    private void selfInitialize(ServletContext servletContext) {
        ServletRegistration.Dynamic servletRegistration = servletContext.addServlet(DISPATCHER_SERVLET_NAME, new DispatcherServlet());
        servletRegistration.setLoadOnStartup(1);
        servletRegistration.addMapping("/");

        FilterRegistration.Dynamic filterRegistration = servletContext.addFilter("encodingFilter", new CharacterEncodingFilter());
        filterRegistration.addMappingForUrlPatterns(null, false, "/*");
    }
}
