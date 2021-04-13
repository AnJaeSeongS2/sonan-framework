package org.sonan.framework.container.server;

import org.sonan.logback.support.Markers;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WebApplicationServer 인터페이스화 할 것. SPI염두할 것.
 */
public class TomcatWebServer {
    private static final Logger logger = LoggerFactory.getLogger(TomcatWebServer.class);

    private final Tomcat tomcat;

    public TomcatWebServer(Tomcat tomcat) {
        this.tomcat = tomcat;
        initialize();
    }

    private void initialize() {
        try {
            this.tomcat.start();
            Thread awaitThread = new Thread("container") {
                @Override
                public void run() {
                    TomcatWebServer.this.tomcat.getServer().await();
                }
            };
            awaitThread.setContextClassLoader(getClass().getClassLoader());
            awaitThread.setDaemon(false);
            awaitThread.start();
        } catch (Exception ex) {
            throw new WebServerException("Unable to start embedded Tomcat", ex);
        }
    }

    public void start() {
        if (logger.isInfoEnabled(Markers.LIFE_CYCLE.get()))
            logger.info(Markers.LIFE_CYCLE.get(),"Tomcat started on port(s): " + tomcat.getConnector().getPort());
    }

    public void stop()  {
        try {
            if (logger.isInfoEnabled(Markers.LIFE_CYCLE.get()))
                logger.info(Markers.LIFE_CYCLE.get(), "Tomcat stopped on port(s): " + tomcat.getConnector().getPort());
            this.tomcat.stop();
            this.tomcat.destroy();
        } catch (LifecycleException ex) {
            // swallow and continue
        }
    }
}
