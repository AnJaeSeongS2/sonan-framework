package com.woowahan.framework.container.server;

import com.woowahan.framework.container.servlet.ServletContextInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

public class TomcatStarter implements ServletContainerInitializer {
    private static final Logger logger = LoggerFactory.getLogger(TomcatStarter.class);
    private final ServletContextInitializer initializers;

    public TomcatStarter(ServletContextInitializer initializers) {
        this.initializers = initializers;
    }

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        logger.info("<<<TEST>>> Logback Tomcat Starting...");
        initializers.onStartup(ctx);
    }
}
