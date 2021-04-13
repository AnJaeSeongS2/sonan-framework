package org.sonan.framework.web.view;

/**
 * 내부 리소스를 찾아준다.
 * Created by Jaeseong on 2021/04/08
 * Git Hub : https://github.com/AnJaeSeongS2
 */

public class InternalResourceViewResolver {

}
//public class InternalResourceViewResolver extends ViewResolver {
//    private static final Logger logger = LoggerFactory.getLogger(InternalResourceViewResolver.class);
//    private String prefix;
//    private String suffix;
//    private String contentType;
//
//    public InternalResourceViewResolver(int priority, String prefix, String suffix, String contentType) {
//        super(priority);
//        this.prefix = prefix;
//        this.suffix = suffix;
//        this.contentType = contentType;
//    }
//
//    public Object resolve(Object beforeResolve, HttpServletRequest req, HttpServletResponse resp) throws FailedViewResolveException, IOException {
//
        // TODO: NOT WOKRING. NOT YET.
//
//        String resourcePath = prefix + beforeResolve + suffix;
//        URL resourceUrl = req.getServletContext().getResource(resourcePath);
//        File resourceFile = new File(resourceUrl.getPath());
//        if (resourceFile.exists()) {
//            // multi operation IO가 아니므로 그냥 현 thread에서 처리.
//            ServletOutputStream os = resp.getOutputStream();
//            AsyncContext asyncCtx = req.startAsync();
//            if (os.isReady()) {
//                // TODO: 파일 자체가 너무 클 경우 response 1건으로 처리할 지 나눠 처리할 지 추후 설계필요.
//                byte[] buffer = Files.toByteArray(resourceFile);
//                if (buffer != null) {
//                    os.write(buffer);
//                }
//                asyncCtx.complete();
//            }
//            if (logger.isDebugEnabled(Markers.MESSAGE.get()))
//                logger.debug(Markers.MESSAGE.get(), "Success async resolveToClient.");

//            // 직접 file io를 하지 않고, async처리한다.
//            ServletOutputStream os = resp.getOutputStream();
//            AsyncContext asyncCtx = req.startAsync();
//            os.setWriteListener(new WriteListener() {
//                @Override
//                public void onWritePossible() throws IOException {
//                    if (os.isReady()) {
//                        // TODO: 파일 자체가 너무 클 경우 response 1건으로 처리할 지 나눠 처리할 지 추후 설계필요.
//                        byte[] buffer = Files.toByteArray(resourceFile);
//                        if (buffer != null) {
//                            os.write(buffer);
//                        }
//                        asyncCtx.complete();
//                    }
//                    if (logger.isDebugEnabled(Markers.MESSAGE.get()))
//                        logger.debug(Markers.MESSAGE.get(), "Success async resolveToClient.");
//                }
//
//                @Override
//                public void onError(Throwable t) {
//                    if (logger.isErrorEnabled(Markers.MESSAGE.get()))
//                        logger.error(Markers.MESSAGE.get(), "Failed async resolveToClient.", t);
//                    // error page가 없을 경우 대비해 complete call한다. ref: https://docs.oracle.com/javaee/7/api/javax/servlet/AsyncContext.html
//                    asyncCtx.complete();
//                }
//            });
//        } else {
//            // 404 NotFound.
//            resp.setStatus(404);
//            resp.getWriter().write("Not Found Resource.");
//        }
//        return null;
//    }
//}