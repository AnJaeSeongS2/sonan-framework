package org.sonan.framework.container.servlet;

import org.sonan.framework.container.ContainerBootstrapper;
import org.sonan.framework.container.lifecycle.SimpleLifeCycle;
import org.sonan.framework.container.lifecycle.StaticLifeCycleEventBus;
import org.sonan.framework.container.server.TomcatWebServer;
import org.sonan.framework.container.server.TomcatWebServerFactory;
import org.sonan.framework.container.servlet.filter.CachedRequestBodyFilter;
import org.sonan.framework.container.servlet.filter.CharacterEncodingFilter;
import org.sonan.framework.container.throwable.BootingFailException;
import org.sonan.framework.context.ApplicationContext;
import org.sonan.framework.context.GenericApplicationContext;
import org.sonan.framework.context.bean.BeanDefinition;
import org.sonan.framework.context.bean.BeanDefinitionHolder;
import org.sonan.framework.context.bean.throwable.BeanCreationFailedException;
import org.sonan.framework.context.bean.throwable.BeanDefinitionNotGeneratedException;
import org.sonan.logback.support.Markers;
import org.sonan.util.annotation.Nullable;
import org.apache.catalina.servlets.DefaultServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.net.URLClassLoader;

public class ServletBootstrapperDefault implements ContainerBootstrapper {
    private static final Logger logger = LoggerFactory.getLogger(ServletBootstrapperDefault.class);
    public static final String DEFAULT_SERVLET_NAME = "default";
    public static final String DISPATCHER_SERVLET_NAME = "dispatcherServlet";

    private TomcatWebServer webServer;
    private ApplicationContext rootAppCtx;
    private BeanDefinitionHolder beanDefinitionHolder;

    public void boot(@Nullable String basePackageForComponentScan) throws BootingFailException {
        if (logger.isDebugEnabled(Markers.LIFE_CYCLE.get()))
            logger.debug(Markers.LIFE_CYCLE.get(), "try booting ServletBootstrapperDefault");
        try {
            initBeanDefinitionHolder((URLClassLoader) Thread.currentThread().getContextClassLoader(), basePackageForComponentScan);
            createWebServer();
            registerShutdownHandler();
            start();
            if (logger.isDebugEnabled(Markers.LIFE_CYCLE.get()))
                logger.debug(Markers.LIFE_CYCLE.get(), "send AFTER_START_EVENT on booting ServletBootstrapperDefault");
            StaticLifeCycleEventBus.send(SimpleLifeCycle.AFTER_START_EVENT);
            if (logger.isDebugEnabled(Markers.LIFE_CYCLE.get()))
                logger.debug(Markers.LIFE_CYCLE.get(), "success booting ServletBootstrapperDefault");
            printSuccessStart();
        } catch (Exception e) {
            if (logger.isErrorEnabled(Markers.LIFE_CYCLE.get()))
                logger.error(Markers.LIFE_CYCLE.get(), "fail booting ServletBootstrapperDefault", e);
            throw new BootingFailException(e.getMessage(), e);
        }
    }

    private void initBeanDefinitionHolder(@Nullable URLClassLoader parentClassLoader, @Nullable String basePackageForComponentScan) throws BeanDefinitionNotGeneratedException {
        beanDefinitionHolder = new BeanDefinitionHolder(parentClassLoader, basePackageForComponentScan);
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

    /**
     * @see ServletContextInitializer::onStartup(ServletContext) method
     * @param servletContext
     * @throws ServletException
     */
    private void selfInitialize(ServletContext servletContext) throws ServletException {
        // DispatcherServlet으로만 동작하게끔 개선중...
        // registerDefaultServlet(servletContext);
        registerDispatcherServlet(servletContext);
    }

    /**
     * html, css, js 같은 resource를 client에게 제공한다. Serlvet이 제공해주는 DefaultServlet을 사용한다.
     * ViewResolver 로 작성하려했던 기반은 MessageResolver 에서 확인 가능하다.
     *
     * DispatcherServlet으로만 동작가능하게 개선 중... 미사용 될 예정.
     * @see org.sonan.framework.web.protocol.MessageResolver
     *
     * @param servletContext
     * @throws ServletException
     */
    private void registerDefaultServlet(ServletContext servletContext) throws ServletException {
        ServletRegistration.Dynamic servletRegistration = servletContext.addServlet(DEFAULT_SERVLET_NAME, new DefaultServlet());
        servletRegistration.setLoadOnStartup(1);
        servletRegistration.addMapping("/static/*");
    }

    private void registerDispatcherServlet(ServletContext servletContext) throws ServletException {
        ServletRegistration.Dynamic servletRegistration = servletContext.addServlet(DISPATCHER_SERVLET_NAME, new DispatcherServlet());
        servletRegistration.setLoadOnStartup(2);
        servletRegistration.addMapping("/");

        FilterRegistration.Dynamic filterRegistration = servletContext.addFilter("CharacterEncodingFilter", new CharacterEncodingFilter());
        filterRegistration.addMappingForUrlPatterns(null, false, "/*");
        FilterRegistration.Dynamic registrationCachedRequestBodyFiler = servletContext.addFilter("CachedRequestBodyFilter", new CachedRequestBodyFilter());
        registrationCachedRequestBodyFiler.addMappingForUrlPatterns(null, false, "/*");

        if (servletContext.getAttribute(ApplicationContext.ROOT_APPLICATION_CONTEXT_ATTRIBUTE_KEY) == null) {
            rootAppCtx = new GenericApplicationContext<>(null, servletContext, (servletCtx) ->
                    (ApplicationContext) servletContext.getAttribute(ApplicationContext.ROOT_APPLICATION_CONTEXT_ATTRIBUTE_KEY)
            );
            servletContext.setAttribute(ApplicationContext.ROOT_APPLICATION_CONTEXT_ATTRIBUTE_KEY, rootAppCtx);
        }

        // TODO: 지금은 rootAppCtx만 사용하고 있지만, framework 상 공통 로직 @Service 같은 것은 root에, 개별 Servlet용 @Controller 등은 root > child 측에 저장하게끔 한다. (scope분리)
        try {
            // bean definition 등록.
            for (BeanDefinition definition : beanDefinitionHolder.get()) {
                rootAppCtx.register(definition);
            }

            // TODO: lazy-initialize 옵션은 향후 지원.
            // pre-initialize beans
            refreshAllBeans();
        } catch (Exception e) {
            if (logger.isErrorEnabled(Markers.LIFE_CYCLE.get()))
                logger.error(Markers.LIFE_CYCLE.get(), "servletContextInit failed", e);
            throw new ServletException(e.getMessage(), e);
        }
    }

    /**
     * 관리중인 모든 빈(ex: singleton bean)을 모두 재 할당한다.
     */
    public void refreshAllBeans() throws BeanCreationFailedException {
        rootAppCtx.refreshInstances();
    }

    /**
     * sonan web FW booting success.
     */
    private void printSuccessStart() {
        if (logger.isInfoEnabled(Markers.LIFE_CYCLE.get()))
            logger.info(Markers.LIFE_CYCLE.get(), "\n" +
" _____                              ______                                                       _    \n" +
"/  ___|                             |  ___|                                                     | |   \n" +
"\\ `--.   ___   _ __    __ _  _ __   | |_    _ __   __ _  _ __ ___    ___ __      __  ___   _ __ | | __\n" +
" `--. \\ / _ \\ | '_ \\  / _` || '_ \\  |  _|  | '__| / _` || '_ ` _ \\  / _ \\\\ \\ /\\ / / / _ \\ | '__|| |/ /\n" +
"/\\__/ /| (_) || | | || (_| || | | | | |    | |   | (_| || | | | | ||  __/ \\ V  V / | (_) || |   |   < \n" +
"\\____/  \\___/ |_| |_| \\__,_||_| |_| \\_|    |_|    \\__,_||_| |_| |_| \\___|  \\_/\\_/   \\___/ |_|   |_|\\_\\" +
"\n" +
"success booting sonan web framework with container.");
    }
}
