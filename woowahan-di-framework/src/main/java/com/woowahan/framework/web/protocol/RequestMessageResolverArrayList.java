package com.woowahan.framework.web.protocol;

import java.util.ArrayList;

/**
 * RequestMessage에 대한 MessageResolver 를 등록하고있는 ArrayList이다.
 * 이 클래스를 상속한 별도 클래스를 Bean으로 등록할 때, 이 클래스의 Bean으로 등록해 놓으면 프레임워크는 별도 클래스의 list를 활용하게 된다.
 *
 * Created by Jaeseong on 2021/04/11
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class RequestMessageResolverArrayList extends ArrayList<MessageResolver> {
}
