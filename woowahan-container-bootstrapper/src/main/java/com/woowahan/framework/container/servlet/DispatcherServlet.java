package com.woowahan.framework.container.servlet;

import com.woowahan.framework.context.bean.BeanManager;
import com.woowahan.framework.web.Router;
import com.woowahan.framework.web.annotation.RequestMethod;
import com.woowahan.framework.web.protocol.RequestMessage;
import com.woowahan.framework.web.protocol.ResponseMessage;
import com.woowahan.framework.web.throwable.FailedRouteException;
import com.woowahan.framework.web.view.InternalResourceViewResolver;
import com.woowahan.framework.web.view.ViewResolver;
import com.woowahan.logback.support.Markers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.TreeSet;

import static com.woowahan.framework.container.servlet.Constants.HTML_CONTEXT_TYPE_RES;

/**
 * request를 적절히 Routing해 원하는 로직 수행이후 적절히 View를 client에게 제공한다.
 *
 * @see ServletRequestMessageReceiver
 * @see Router
 * @see ViewResolver
 * @see ServletResponseMessageSender
 * Created by Jaeseong on 2021/04/08
 * Git Hub : https://github.com/AnJaeSeongS2
 */
@WebServlet
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
        viewResolvers.add(new InternalResourceViewResolver(1, "resources/static/", ".html", HTML_CONTEXT_TYPE_RES));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doService(req, resp, RequestMethod.GET);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doService(req, resp, RequestMethod.POST);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doService(req, resp, RequestMethod.PUT);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doService(req, resp, RequestMethod.DELETE);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private void doService(HttpServletRequest req, HttpServletResponse resp, RequestMethod reqMethod) throws ServletException, IOException {
        try {
            if (logger.isTraceEnabled(Markers.MESSAGE.get()))
                logger.trace(Markers.MESSAGE.get(), String.format("Trying DispatcherServlet's service."));
            try {
                RequestMessage reqMessage = ServletRequestMessageReceiver.getInstance().receive(req);

                // use singleton Router.
                Router router = (Router) BeanManager.getInstance().getBean(Router.class, null);
                ResponseMessage respMessage = null;
                try {
                    if (logger.isTraceEnabled(Markers.MESSAGE.get()))
                        logger.trace(Markers.MESSAGE.get(), String.format("Trying invoke Routed Method."));
                    respMessage = router.route(reqMessage);
                } catch (FailedRouteException e) {
                    // 404 Not Found.
                    if (logger.isTraceEnabled(Markers.MESSAGE.get()))
                        logger.trace(Markers.MESSAGE.get(), String.format("Failed invoke Routed Method."));
                    // direct write response.
                    ServletResponseMessageSender.getInstance().send(resp, 404, null);
                    return;
                }
                if (logger.isTraceEnabled(Markers.MESSAGE.get()))
                    logger.trace(Markers.MESSAGE.get(), String.format("Success invoke Routed Method."));

                Object resultAfterViewResolve = respMessage.getMessage();
                if (!respMessage.isResolvedMessage()) {
                    for (ViewResolver viewResolver : viewResolvers) {
                        try {
                            // viewResolver 가 알아서 response를 날려준다.
                            viewResolver.resolve(resultAfterViewResolve, req, resp);
                            break;
                        } catch(Exception e) {
                            if (logger.isWarnEnabled())
                                logger.warn(String.format("failed resolve View. so, continue resolve other view with next prority. current View Resolver : %s", viewResolver));
                        }
                    }
                } else {
                    // TODO: 역할 분담이 이루어져야한다.
                    // direct write response.
                    ServletResponseMessageSender.getInstance().send(resp, 200, resultAfterViewResolve);
                }
            } catch (Exception e) {
                if (logger.isErrorEnabled(Markers.MESSAGE.get()))
                    logger.error(Markers.MESSAGE.get(), String.format("Failed DispatcherServlet's service. %s"), e.toString());
                if (logger.isTraceEnabled(Markers.MESSAGE.get()))
                    logger.trace(Markers.MESSAGE.get(), "Show DispatcherServlet's service Stacktrace", e);
                throw new ServletException(e.getMessage(), e);
            }
        } finally {
            if (logger.isTraceEnabled(Markers.MESSAGE.get()))
                logger.trace(Markers.MESSAGE.get(), String.format("Success DispatcherServlet's service."));
        }
    }
}
