package org.sonan.framework.container.servlet.view;

import org.sonan.framework.context.annotation.Service;
import org.sonan.framework.web.protocol.ResponseMessageResolverArrayList;

/**
 * Servlet의 default viewResolverList 이다.
 *
 * TODO: 지금은 이 ViewResolverList만 등록되는데, 차후 변경가능하게.
 * Router에서 org.sonan.framework.web.protocol.ResponseMessageResolverArrayList 로 등록된 Bean으로써 읽게된다.
 * @see org.sonan.framework.web.protocol.ResponseMessageResolverArrayList
 * @see org.sonan.framework.web.Router
 * Created by Jaeseong on 2021/04/11
 * Git Hub : https://github.com/AnJaeSeongS2
 */
@Service(identifyClassCanonicalName = "org.sonan.framework.web.protocol.ResponseMessageResolverArrayList")
public class DefaultServletViewResolverList extends ResponseMessageResolverArrayList {

    public DefaultServletViewResolverList() {
        add(new InternalResourceViewResolver(1, "/static/", ""));
    }
}
