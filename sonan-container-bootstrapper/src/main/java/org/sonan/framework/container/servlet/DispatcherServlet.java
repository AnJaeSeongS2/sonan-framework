package org.sonan.framework.container.servlet;

import org.sonan.framework.container.servlet.protocol.ServletMessageChannel;
import org.sonan.framework.container.servlet.protocol.ServletResponseMessage;
import org.sonan.framework.context.bean.BeanManager;
import org.sonan.framework.web.Router;
import org.sonan.framework.web.protocol.*;
import org.sonan.framework.web.throwable.FailedResolveException;
import org.sonan.framework.web.throwable.FailedRouteException;
import org.sonan.logback.support.Markers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Dispatcher는 이름그대로의 동작을 아래 시나리오대로 동작한다.
 * 1. HttpServletRequest, HttpServletResponse 를 이용해 ServletMessageChannel을 만든다.
 * 2. ServletMessageChannel을 client에게서 전달받은 Message를 얻어낸다.
 * 3. Router에게 적절한 Controller로직을 태워주게 한 뒤 로직 이후 받은 Message를 받는다.
 * 4. 해당 Message를 ViewResolver 로직에 태운다.
 * 4. ServletMessageChannel를 통해 Resolved Message를 client로 제공한다.
 *
 * HttpServletRequest, HttpServletResponse
 * Message 인터페이스 기반으로 ServletMessageChannel 이 활용된다.
 * Router 는 적절한 Controller로직을 태워준다.
 *
 * (모듈이나 컴포넌트간의 관심사를 분리시키려 의도했다.)
 * @see ServletMessageChannel
 * @see Router
 * @see org.sonan.framework.context.annotation.Controller
 * Created by Jaeseong on 2021/04/08
 * Git Hub : https://github.com/AnJaeSeongS2
 */
@WebServlet
public class DispatcherServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    /**
     * do nothing. servlet must no init on Constructor.
     */
    public DispatcherServlet() {
        super();
    }

    @Override
    public void init() throws ServletException {
        super.init();
//        viewResolvers = new TreeSet<>();
//        viewResolvers.add(new InternalResourceViewResolver(1, "resources/static/", ".html", HTML_CONTENT_TYPE_RES));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doService(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doService(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doService(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doService(req, resp);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doService(req, resp);
    }

    private void doService(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (logger.isTraceEnabled(Markers.MESSAGE.get()))
                logger.trace(Markers.MESSAGE.get(), String.format("Trying DispatcherServlet's service."));
            ServletMessageChannel messageChannel = new ServletMessageChannel(req, resp);
            RequestMessage reqMessage = null;
            try {
                reqMessage = (RequestMessage) messageChannel.receive();
            } catch (Exception e) {
                throw new IOException(String.format("Not support this message's class on DispatcherServlet's Request. receivedMessage's class : %s", reqMessage.getClass()));
            }

            // use singleton Router bean.
            Router router = (Router) BeanManager.getInstance().getBean(Router.class, null);
            Message sendMessage = null;
            ResponseMessage respMessage = null;
            try {
                if (logger.isTraceEnabled(Markers.MESSAGE.get()))
                    logger.trace(Markers.MESSAGE.get(), String.format("Trying invoke Routed Method."));
                respMessage = router.route(reqMessage);
            } catch (FailedRouteException e) {
                if (logger.isDebugEnabled(Markers.MESSAGE.get()))
                    logger.debug(Markers.MESSAGE.get(), String.format("Failed invoke Routed Method. %s", e.getMessage()));
                // 404 Not Found.
                respMessage = new ServletResponseMessage(reqMessage.getVendor(), reqMessage.getUrl(), null, null);
            }

            // let's resolve message.
            respMessage = resolve(respMessage);
            if (logger.isTraceEnabled(Markers.MESSAGE.get()))
                logger.trace(Markers.MESSAGE.get(), String.format("Success invoke Routed Method."));
            messageChannel.send(respMessage);
        } catch(Exception e) {
            if (logger.isErrorEnabled(Markers.MESSAGE.get()))
                logger.error(Markers.MESSAGE.get(), String.format("Failed DispatcherServlet's service. %s", e.getMessage()));
            if (logger.isTraceEnabled(Markers.MESSAGE.get()))
                logger.trace(Markers.MESSAGE.get(), "Show DispatcherServlet's service Stacktrace", e);
            throw new ServletException(e.getMessage(), e);
        }
        if (logger.isTraceEnabled(Markers.MESSAGE.get()))
            logger.trace(Markers.MESSAGE.get(), String.format("Success DispatcherServlet's service."));
    }

    private ResponseMessage resolve(ResponseMessage messageBeforeResolve) {
        ResponseMessage messageAfterResolve = messageBeforeResolve;

        // let's resolve message.
        ResponseMessageResolverArrayList resolverList = null;
        try {
            resolverList = (ResponseMessageResolverArrayList) BeanManager.getInstance().getBean(ResponseMessageResolverArrayList.class, null);
            for (MessageResolver messageResolver : resolverList) {
                // resolve by order.
                try {
                    messageAfterResolve = (ResponseMessage) messageResolver.resolve(messageBeforeResolve);
                } catch (FailedResolveException e) {
                    // retry next resolve.
                    // using immutable messageBeforeResolve
                }
                // resolve success.
                break;
            }
        } catch (Exception e) {
            if (logger.isWarnEnabled())
                logger.warn(String.format("ResponseMessageResolverArrayList's bean is not exists."));
        }
        return messageAfterResolve;
    }
}
