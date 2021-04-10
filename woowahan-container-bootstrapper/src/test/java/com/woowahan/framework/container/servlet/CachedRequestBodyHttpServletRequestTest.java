package com.woowahan.framework.container.servlet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * TODO: Serlvet에 대한 HttpServletRequest에 대한 테스트는 좀 더 공부가 필요하다. Mock으로 테스트 한다 한들, 실효성이 있는 테스트인지 공부가 필요.
 *
 * Created by Jaeseong on 2021/04/10
 * Git Hub : https://github.com/AnJaeSeongS2
 */
class CachedRequestBodyHttpServletRequestTest {

    HttpServletRequest servletRequest;
    CachedRequestBodyHttpServletRequest request;

    @Test
    @BeforeEach
    void makeInstance() throws IOException {
//        ServletInputStream mockServletInputStream = mock(ServletInputStream.class);
//        ServletRequest req = mock(ServletRequest.class);
//        when(req.getInputStream()).thenReturn(mockServletInputStream);
//        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("asdfasdf".getBytes());
//        when(mockServletInputStream.read(Matchers.<byte[]>any(), anyInt(), anyInt())).thenAnswer(new Answer<Integer>() {
//            @Override
//            public Integer answer(InvocationOnMock invocationOnMock) throws Throwable {
//                Object[] args = invocationOnMock.getArguments();
//                byte[] output = (byte[]) args[0];
//                int offset = (int) args[1];
//                int length = (int) args[2];
//                return byteArrayInputStream.read(output, offset, length);
//            }
//        });
//        assertEquals("asdfasdf", RequestBodyReader.read(req));
//
//        request = new CachedRequestBodyHttpServletRequest(servletRequest);
//
    }

    @Test
    void getInputStream() {
    }

    @Test
    void getReader() {
    }

    @Test
    void getCachedRequestBody() {

    }
}