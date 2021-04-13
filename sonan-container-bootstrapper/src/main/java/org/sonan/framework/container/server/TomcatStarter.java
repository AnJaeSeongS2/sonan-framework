package org.sonan.framework.container.server;

import org.sonan.framework.container.servlet.ServletContextInitializer;
import org.sonan.logback.support.Markers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

public class TomcatStarter implements ServletContainerInitializer {
    private static final Logger logger = LoggerFactory.getLogger(TomcatStarter.class);
    private final ServletContextInitializer initializer;

    public TomcatStarter(ServletContextInitializer initializer) {
        this.initializer = initializer;
    }

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        if (logger.isDebugEnabled(Markers.LIFE_CYCLE.get()))
            logger.debug(Markers.LIFE_CYCLE.get(), "try Tomcat's ServletContainerInitializer onStartup method.");
        initializer.onStartup(ctx);
        if (logger.isDebugEnabled(Markers.LIFE_CYCLE.get()))
            logger.debug(Markers.LIFE_CYCLE.get(), "success Tomcat's ServletContainerInitializer onStartup method.");
    }
}
