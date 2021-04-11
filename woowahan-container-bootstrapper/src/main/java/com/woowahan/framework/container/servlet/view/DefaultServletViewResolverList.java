package com.woowahan.framework.container.servlet.view;

import com.woowahan.framework.context.annotation.Service;
import com.woowahan.framework.web.protocol.ResponseMessageResolverArrayList;

/**
 * Servlet의 default viewResolverList 이다.
 *
 * TODO: 지금은 이 ViewResolverList만 등록되는데, 차후 변경가능하게.
 * Router에서 com.woowahan.framework.web.protocol.ResponseMessageResolverArrayList 로 등록된 Bean으로써 읽게된다.
 * @see com.woowahan.framework.web.protocol.ResponseMessageResolverArrayList
 * @see com.woowahan.framework.web.Router
 * Created by Jaeseong on 2021/04/11
 * Git Hub : https://github.com/AnJaeSeongS2
 */
@Service(identifyClassCanonicalName = "com.woowahan.framework.web.protocol.ResponseMessageResolverArrayList")
public class DefaultServletViewResolverList extends ResponseMessageResolverArrayList {

    public DefaultServletViewResolverList() {
        add(new InternalResourceViewResolver(1, "/static/", ""));
    }
}
