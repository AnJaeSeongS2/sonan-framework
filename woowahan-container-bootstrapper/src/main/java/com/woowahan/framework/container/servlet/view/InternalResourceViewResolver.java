package com.woowahan.framework.container.servlet.view;

import com.google.common.io.Files;
import com.woowahan.framework.container.servlet.protocol.ServletResponseMessage;
import com.woowahan.framework.container.servlet.resource.ResourceFileMeta;
import com.woowahan.framework.web.protocol.Message;
import com.woowahan.framework.web.protocol.ResponseMessage;
import com.woowahan.framework.web.throwable.FailedResolveException;
import com.woowahan.framework.web.view.ViewResolver;
import com.woowahan.logback.support.Markers;
import org.apache.catalina.loader.ParallelWebappClassLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;

import static com.woowahan.framework.container.servlet.Constants.DEFAULT_ENCODING;

/**
 * FILE IO 를 하기 때문에 느린 점을 감안해야한다.
 *
 * Created by Jaeseong on 2021/04/10
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class InternalResourceViewResolver extends ViewResolver {
    private static final Logger logger = LoggerFactory.getLogger(InternalResourceViewResolver.class);
    private final String prefix;
    private final String suffix;

    public InternalResourceViewResolver(int priority, String prefix, String suffix) {
        super(priority);
        this.prefix = prefix;
        this.suffix = suffix;
    }

    @Override
    public Message resolve(Message messageBeforeResolve) throws FailedResolveException {
        String resourcePath = prefix + messageBeforeResolve.getMessage() + suffix;
        try {
            //TODO: restore as production mode.
//            URL resourceUrl = req.getServletContext().getResource(resourcePath);
//            return getResourceIfExists(resourceUrl, (ResponseMessage) messageBeforeResolve);
            // dev embedded tomcat case... (for development.)
            throw new ResourceNotFoundException("embedded tomcat case... (for development.)");
        } catch (ResourceNotFoundException e) {
            // dev embedded tomcat case... (for development.)
            if (logger.isTraceEnabled(Markers.DEV.get()))
                logger.trace(Markers.DEV.get(), String.format("Search resource on [ClassPath directory]. resourcePath : %s", resourcePath));
            ClassLoader searchRootClassLoader = Thread.currentThread().getContextClassLoader();
            if (Thread.currentThread().getContextClassLoader() instanceof ParallelWebappClassLoader) {
                searchRootClassLoader = Thread.currentThread().getContextClassLoader().getParent();
            }

            for (URL url : ((URLClassLoader) searchRootClassLoader).getURLs()) {
                URL resourceUrlOnEmbeddedCase = null;
                try {
                    if (logger.isTraceEnabled(Markers.DEV.get()))
                        logger.trace(Markers.DEV.get(), String.format("Search resource on [%s]. resourcePath : %s", url.getPath(), resourcePath));
                    resourceUrlOnEmbeddedCase = Paths.get(url.getPath(), resourcePath).toUri().toURL();
                } catch (MalformedURLException malformedURLException) {
                    // skip.
                    continue;
                }
                try {
                    return getResourceIfExists(resourceUrlOnEmbeddedCase, (ResponseMessage) messageBeforeResolve);
                } catch (ResourceNotFoundException e2) {
                    continue;
                }
            }
        }
        throw new FailedResolveException(String.format("cannot find resource. resourcePath : %s", resourcePath));
    }

    private ResponseMessage getResourceIfExists(URL resourceUrl, ResponseMessage messageBeforeResolve) throws ResourceNotFoundException {
        File resourceFile = new File(resourceUrl.getPath());
        if (resourceFile.exists()) {
            // multi operation IO가 아니므로 그냥 현 thread에서 처리.
            // TODO: 파일 자체가 너무 클 경우 response 1건으로 처리할 지 나눠 처리할 지 추후 설계필요.
            byte[] buffer = null;
            try {
                buffer = Files.toByteArray(resourceFile);
                String contentType = null;
                for (ResourceFileMeta meta : ResourceFileMeta.values()) {
                    if (resourceFile.getPath().endsWith(meta.getSuffix())) {
                        contentType = meta.getContentType();
                        break;
                    }
                }
                ResponseMessage result = new ServletResponseMessage(messageBeforeResolve.getVendor(), new String(buffer, DEFAULT_ENCODING), 200, contentType);
                if (logger.isDebugEnabled(Markers.MESSAGE.get()))
                    logger.debug(Markers.MESSAGE.get(), String.format("Success read resource. resourceUrl : %s", resourceUrl.getPath()));
                return result;
            } catch (IOException e) {
                throw new ResourceNotFoundException(e.getMessage(), e);
            }
        }
        throw new ResourceNotFoundException("Not Exists.");
    }
}
