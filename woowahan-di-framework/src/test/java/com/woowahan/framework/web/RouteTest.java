package com.woowahan.framework.web;

import com.woowahan.framework.context.annotation.Autowired;
import com.woowahan.framework.context.annotation.Controller;
import com.woowahan.framework.json.JacksonUtil;
import com.woowahan.framework.json.throwable.FailedConvertJsonException;
import com.woowahan.framework.throwable.FailedDeleteException;
import com.woowahan.framework.throwable.FailedGetException;
import com.woowahan.framework.throwable.FailedPostException;
import com.woowahan.framework.throwable.FailedPutException;
import com.woowahan.framework.web.annotation.PathVariable;
import com.woowahan.framework.web.annotation.RequestMapping;
import com.woowahan.framework.web.annotation.RequestMethod;
import com.woowahan.util.reflect.ReflectionUtil;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Jaeseong on 2021/04/07
 * Git Hub : https://github.com/AnJaeSeongS2
 */
class RouteTest {

    @Test
    void invokeAfterControllerCreation() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Route route = (Route) ReflectionUtil.newInstanceAnyway(Route.class);
        TestController controller = new TestController();
        route.invokeAfterControllerCreation(controller);

        controller.getClass().getAnnotation(RequestMapping.class) : nullable
        // controller.getClass().getAnnotation(RequestMapping.class).value() : /shops
        // controller.getClass().getAnnotation(RequestMapping.class).method() : [] n개

        //
        // controller.getClass().getMethods()[3].getAnnotation(RequestMapping.class) : nullable
    }
}

@Controller
@RequestMapping("/shops")
class TestController {

    public TestController() {
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getAll() throws FailedGetException, FailedConvertJsonException {
        return "";
    }

    @RequestMapping(method = RequestMethod.POST)
    public void post() throws FailedPostException {
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void put(@PathVariable Integer id) throws FailedPutException {
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable Integer id) throws FailedDeleteException {
        return "";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String get(@PathVariable Integer id) throws FailedGetException, FailedConvertJsonException {
        return "";
    }
}