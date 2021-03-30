package com.woowahan.framework.container.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * todo : Implementation {@link javax.servlet.Servlet#service}
 */
public class DispatcherServlet extends HttpServlet {
    public DispatcherServlet() {
        super();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);


        // set ContextManager -> setCurrentContext as getServletContext();

        // Bean


        //
    }
}
