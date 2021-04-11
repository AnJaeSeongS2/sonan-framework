package com.woowahan.framework.container.servlet.filter;

import com.woowahan.framework.container.servlet.CachedRequestBodyHttpServletRequest;
import com.woowahan.framework.context.annotation.Service;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.woowahan.framework.container.servlet.Constants.APPLICATION_CONTENT_TYPE_REQ;

/**
 * @see CachedRequestBodyHttpServletRequest
 * Created by Jaeseong on 2021/04/08
 * Git Hub : https://github.com/AnJaeSeongS2
 */
@Service
public class CachedRequestBodyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        if (APPLICATION_CONTENT_TYPE_REQ.equals(req.getContentType())) {
            filterChain.doFilter(new CachedRequestBodyHttpServletRequest((HttpServletRequest) req), resp);
            return;
        }
        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}
