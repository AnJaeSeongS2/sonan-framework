package com.woowahan.framework.container.servlet;

import com.woowahan.logback.support.Markers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * requestBody를 버퍼에 저장해놔 여러번 활용한다.
 * getInputStream은 cachedBytes저장해둔 requestBody를 CachedServletInputStream으로 변환해 제공한다.
 *
 * Created by Jaeseong on 2021/04/08
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class CachedRequestBodyHttpServletRequest extends HttpServletRequestWrapper {
    private static final Logger logger = LoggerFactory.getLogger(CachedRequestBodyHttpServletRequest.class);
    private static final int STREAM_COPY_BUFFER_SIZE = 8192;
    private String requestBody;

    public CachedRequestBodyHttpServletRequest(HttpServletRequest request) {
        super(request);
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
            if (logger.isErrorEnabled())
                logger.error(Markers.MESSAGE.get(), "Failed gen Cached RequestBody", e);
        }
        this.requestBody = sb.toString();
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new CachedServletInputStream(this.requestBody.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public BufferedReader getReader() throws IOException {

        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    public String getRequestBody() {
        return this.requestBody;
    }

//    private void copy(InputStream src, OutputStream target) throws IOException {
//        byte[] buf = new byte[STREAM_COPY_BUFFER_SIZE];
//        int length;
//        while ((length = src.read(buf)) > 0) {
//            target.write(buf, 0, length);
//        }
//    }
}
