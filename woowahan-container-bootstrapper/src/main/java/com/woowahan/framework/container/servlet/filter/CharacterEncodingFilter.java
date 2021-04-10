package com.woowahan.framework.container.servlet.filter;

import javax.servlet.*;
import java.io.IOException;

import static com.woowahan.framework.container.servlet.Constants.DEFAULT_ENCODING;

public class CharacterEncodingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(DEFAULT_ENCODING);
        response.setCharacterEncoding(DEFAULT_ENCODING);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
