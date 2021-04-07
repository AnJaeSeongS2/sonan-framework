package com.woowahan.framework.container.servlet;

import com.woowahan.framework.context.bean.BeanManager;
import com.woowahan.framework.web.Route;
import com.woowahan.framework.web.annotation.RequestMethod;
import com.woowahan.framework.web.view.ConvertJsonViewResolver;
import com.woowahan.framework.web.view.InternalResourceViewResolver;
import com.woowahan.framework.web.view.ViewResolver;
import com.woowahan.logback.support.Markers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.TreeSet;

import static com.woowahan.framework.container.servlet.Constants.REQUEST_BODY;

/**
 * todo : Implementation {@link javax.servlet.Servlet#service}
 */
public class DispatcherServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private TreeSet<ViewResolver> viewResolvers;


    /**
     * do nothing. servlet must no init on Constructor.
     */
    public DispatcherServlet() {
        super();
    }

    @Override
    public void init() throws ServletException {
        super.init();
        viewResolvers = new TreeSet<>();
        viewResolvers.add(new ConvertJsonViewResolver(0));
        viewResolvers.add(new InternalResourceViewResolver(1, "resources/static/", ".html", "text/html;charset=UTF-8"));

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (logger.isTraceEnabled())
                logger.trace(Markers.MESSAGE.get(), String.format("Trying DispatcherServlet's service."));
            try {
                String uri = req.getRequestURI();
                RequestMethod requestMethod = RequestMethod.valueOf(req.getMethod());
                String requestBody = null;
                if (req instanceof CachedRequestBodyHttpServletRequest) {
                    requestBody = ((CachedRequestBodyHttpServletRequest) req).getRequestBody();
                } else {
                    throw new ServletException(String.format("only support CachedRequestBodyHttpServletRequest. current request: %s", req.getClass()));
                }

                // TODO Post,Put 용도
                req.setAttribute(REQUEST_BODY, requestBody);

                Route route = (Route) BeanManager.getInstance().getBean(Route.class, null);

                if (logger.isTraceEnabled())
                    logger.trace(Markers.MESSAGE.get(), String.format("Trying invoke Routed Method"));
                Object result = route.route(uri, requestMethod);
                if (logger.isTraceEnabled())
                    logger.trace(Markers.MESSAGE.get(), String.format("Success invoke Routed Method"));

                Object resultAfterViewResolve = result;
                for (ViewResolver viewResolver : viewResolvers) {
                    try {
                        resultAfterViewResolve = viewResolver.getView(result, resp);
                    } catch(Exception e) {
                        continue;
                    }
                    // resolved.
                    break;
                }

                resp.getWriter().print(resultAfterViewResolve);

            } catch (Exception e) {
                if (logger.isErrorEnabled())
                    logger.error(Markers.MESSAGE.get(), String.format("Failed DispatcherServlet's service. %s"), e.toString());
                if (logger.isTraceEnabled())
                    logger.trace(Markers.MESSAGE.get(), "Show DispatcherServlet's service Stacktrace", e);
                throw new ServletException(e.getMessage(), e);
            }
        } finally {
            if (logger.isTraceEnabled())
                logger.trace(Markers.MESSAGE.get(), String.format("Success DispatcherServlet's service."));
        }
    }
}
