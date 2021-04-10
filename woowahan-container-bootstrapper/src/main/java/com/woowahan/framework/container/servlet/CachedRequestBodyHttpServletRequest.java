package com.woowahan.framework.container.servlet;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * HttpServletRequest의 message(requestBody)는 1회 읽으면 재읽기 불가능한 특징이 있기 때문에, 이 클래스로 requestBody를 버퍼에 저장해놔 여러번 활용한다.
 * TODO: 본래 의도는 ServletFilter 측에서 ServletRequest 를 Wrapping해 연쇄시킬 예정이었다. 지금은 시간 관계상 DispatcherServlet에서 직접 사용중.
 * @see com.woowahan.framework.container.servlet.filter.CachedRequestBodyFilter
 *
 * api중 getCachedRequestBody 사용을 권장.
 *
 * Created by Jaeseong on 2021/04/08
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class CachedRequestBodyHttpServletRequest extends HttpServletRequestWrapper {
    private String requestBody;

    public CachedRequestBodyHttpServletRequest(HttpServletRequest request) throws IOException {
        super(request);
        this.requestBody = RequestBodyReader.read(request);
    }

    /**
     * cachedRequestBody를 활용해 새 CachedServletInputStream을 반환한다..
     * @return
     * @throws IOException
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new CachedServletInputStream(this.requestBody.getBytes(Constants.DEFAULT_ENCODING));
    }

    /**
     * cachedRequestBody를 활용해 새 BufferedReader를 반환한다.
     * @return
     * @throws IOException
     */
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    /**
     * 사용 권장. 캐시된 버퍼를 반환한다.
     * @return
     */
    public String getCachedRequestBody() {
        return this.requestBody;
    }
}
