package com.woowahan.framework.web;

 import com.woowahan.framework.context.annotation.Controller;
import com.woowahan.framework.json.throwable.FailedConvertJsonException;
import com.woowahan.framework.throwable.FailedDeleteException;
import com.woowahan.framework.throwable.FailedGetException;
import com.woowahan.framework.throwable.FailedPostException;
import com.woowahan.framework.throwable.FailedPutException;
import com.woowahan.framework.web.annotation.PathVariable;
import com.woowahan.framework.web.annotation.RequestMapping;
import com.woowahan.framework.web.annotation.RequestMethod;
import com.woowahan.framework.web.throwable.FailedRouteException;
import com.woowahan.util.reflect.ReflectionUtil;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Jaeseong on 2021/04/07
 * Git Hub : https://github.com/AnJaeSeongS2
 */
class RouteTest {

    @Test
    void register() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        Route route = (Route) ReflectionUtil.newInstanceAnyway(Route.class);
        TestController controller = new TestController();
        Method method = ReflectionUtil.getMethodMetaAnyway(TestController.class, (methodOfController) -> methodOfController.getName().equals("delete"));
        route.register("/a/#{id1}#{id2}/c/d/#{id3}", new RequestMethod[]{RequestMethod.GET, RequestMethod.DELETE}, method, controller);
        Map<Method, Object> methodToControllerObject = (Map<Method, Object>) ReflectionUtil.getFieldAnyway(route, "methodToControllerObject");
        assertEquals(controller, methodToControllerObject.get(method));
    }

    @Test
    void testInvokeAfterControllerCreation() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        Route route = (Route) ReflectionUtil.newInstanceAnyway(Route.class);
        TestController controller = new TestController();
        route.invokeAfterControllerCreation(controller);

        Method method = ReflectionUtil.getMethodMetaAnyway(TestController.class, (methodOfController) -> methodOfController.getName().equals("delete"));
        Map<Method, Object> methodToControllerObject = (Map<Method, Object>) ReflectionUtil.getFieldAnyway(route, "methodToControllerObject");
        assertEquals(controller, methodToControllerObject.get(method));

        Map<String, Map<RequestMethod, Map.Entry<Method, Map<Integer, PathVariableModel>>>> routePathToMethod = (Map<String, Map<RequestMethod, Map.Entry<Method, Map<Integer, PathVariableModel>>>>) ReflectionUtil.getFieldAnyway(route, "routePathToMethod");
        assertNotNull(routePathToMethod.get("/shops"));
        assertNotNull(routePathToMethod.get("/shops").get(RequestMethod.GET));
        assertNotNull(routePathToMethod.get("/shops/#{}").get(RequestMethod.GET));
        assertEquals(2, routePathToMethod.get("/shops").size());
        assertEquals(3, routePathToMethod.get("/shops/#{}").size());
    }

    @Test
    void route() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, FailedRouteException {
        Route route = (Route) ReflectionUtil.newInstanceAnyway(Route.class);
        TestController controller = new TestController();
        route.invokeAfterControllerCreation(controller);

        assertEquals("getAll", route.route("/shops", RequestMethod.GET));
        assertNull(route.route("/shops", RequestMethod.POST));
        assertNull(route.route("/shops/#123", RequestMethod.PUT));
        assertEquals("deleteDone3null", route.route("/shops/#3", RequestMethod.DELETE));
        assertEquals("getDone5null", route.route("/shops/#5", RequestMethod.GET));
    }
}

@Controller
@RequestMapping("/shops")
class TestController {

    public TestController() {
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getAll() throws FailedGetException, FailedConvertJsonException {
        return "getAll";
    }

    @RequestMapping(method = RequestMethod.POST)
    public void post() throws FailedPostException {
    }

    @RequestMapping(value = "/#{id}", method = RequestMethod.PUT)
    public void put(@PathVariable("id") Integer id) throws FailedPutException {
    }

    @RequestMapping(value = "/#{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Integer id, String noBound) throws FailedDeleteException {
        return "deleteDone" + id + noBound;
    }

    @RequestMapping(value = "/#{id}", method = RequestMethod.GET)
    public String get(@PathVariable("id") Integer id, String noBound) throws FailedGetException, FailedConvertJsonException {
        return "getDone" + id + noBound;
    }
}