package org.sonan.framework.container.servlet.resource;

import static org.sonan.framework.container.servlet.Constants.*;

/**
 * Created by Jaeseong on 2021/04/12
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public enum ResourceFileMeta {
    JS(JS_FILE_SUFFIX, JS_CONTENT_TYPE_RES),
    HTML(HTML_FILE_SUFFIX, HTML_CONTENT_TYPE_RES),
    CSS(CSS_FILE_SUFFIX, CSS_CONTENT_TYPE_RES);

    private String suffix;
    private String contentType;

    ResourceFileMeta(String suffix, String contentType) {
        this.suffix = suffix;
        this.contentType = contentType;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public String getContentType() {
        return this.contentType;
    }
}
