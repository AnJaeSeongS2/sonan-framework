package org.sonan.framework.web.protocol;

import org.sonan.util.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Jaeseong on 2021/04/11
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class MessageUtil {
    private static final Logger logger = LoggerFactory.getLogger(MessageUtil.class);
    // redirect
    public static final String REDIRECT_PREFIX = "redirect:";
    public static final String HOME_PATH = "/";

    public static boolean isRedirectMessage(@Nullable Message message) {
        if (message != null && message.getMessage() != null && String.valueOf(message.getMessage()).startsWith(REDIRECT_PREFIX))
            return true;
        return false;
    }

    /**
     * redirect: message이면 실제 redirect요청 페이지로 이동한다.
     * @param message
     * @return
     */
    public static String getRedirectPath(@Nullable Message message) {
        if (isRedirectMessage(message))
            return String.valueOf(message.getMessage()).substring(REDIRECT_PREFIX.length());
        // send redirect failed.
        if (logger.isWarnEnabled())
            logger.warn(String.format("Failed RedirectMessage parsing. so, redirect to HOME_PATH(%s) because, ResponseMessage is not valid RedirectMessage.", HOME_PATH));
        return HOME_PATH;
    }
}
