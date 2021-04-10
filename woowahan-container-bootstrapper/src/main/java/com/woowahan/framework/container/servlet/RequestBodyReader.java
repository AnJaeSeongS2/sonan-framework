package com.woowahan.framework.container.servlet;

import com.woowahan.logback.support.Markers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Jaeseong on 2021/04/10
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class RequestBodyReader {
    private static final Logger logger = LoggerFactory.getLogger(RequestBodyReader.class);
    private static final int STREAM_COPY_BUFFER_SIZE = 8192;

    public static String read(ServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (InputStream inputStream = request.getInputStream()) {
            if (inputStream != null) {
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
                    char[] charBuffer = new char[STREAM_COPY_BUFFER_SIZE];
                    int bytesRead = -1;
                    while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                        sb.append(charBuffer, 0, bytesRead);
                    }
                }
            }
        } catch (IOException e) {
            if (logger.isErrorEnabled(Markers.MESSAGE.get()))
                logger.error(Markers.MESSAGE.get(), "Failed gen RequestBody from ServletRequest.", e);
            throw e;
        }
        return sb.toString();
    }
}