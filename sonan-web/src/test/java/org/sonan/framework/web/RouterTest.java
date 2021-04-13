package org.sonan.framework.web;

 import org.sonan.framework.context.annotation.Controller;
 import org.sonan.framework.json.JacksonUtil;
 import org.sonan.framework.json.throwable.FailedConvertJsonException;
import org.sonan.framework.throwable.FailedDeleteException;
import org.sonan.framework.throwable.FailedGetException;
import org.sonan.framework.throwable.FailedPostException;
import org.sonan.framework.throwable.FailedPutException;
 import org.sonan.framework.web.annotation.*;
 import org.sonan.framework.web.protocol.Vendor;
 import org.sonan.framework.web.throwable.FailedRouteException;
import org.sonan.util.reflect.ReflectionUtil;
import org.junit.jupiter.api.Test;

 import java.beans.ConstructorProperties;
 import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
 import java.util.HashMap;
 import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Jaeseong on 2021/04/07
 * Git Hub : https://github.com/AnJaeSeongS2
 */
@SuppressWarnings("unchecked")
class RouterTest {

    @Test
    void register() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        Router router = (Router) ReflectionUtil.newInstanceAnyway(Router.class);
        TestController controller = new TestController();
        Method method = ReflectionUtil.getMethodMetaAnyway(TestController.class, (methodOfController) -> methodOfController.getName().equals("delete"));
        router.register("/a/@{id1}@{id2}/c/d/@{id3}", new RequestMethod[]{RequestMethod.GET, RequestMethod.DELETE}, method, controller);
        Map<Method, Object> methodToControllerObject = (Map<Method, Object>) ReflectionUtil.getFieldAnyway(router, "methodToControllerObject");
        assertEquals(controller, methodToControllerObject.get(method));
    }

    @Test
    void testInvokeAfterControllerCreation() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        Router router = (Router) ReflectionUtil.newInstanceAnyway(Router.class);
        TestController controller = new TestController();
        router.invokeAfterControllerCreation(controller);

        Method method = ReflectionUtil.getMethodMetaAnyway(TestController.class, (methodOfController) -> methodOfController.getName().equals("delete"));
        Map<Method, Object> methodToControllerObject = (Map<Method, Object>) ReflectionUtil.getFieldAnyway(router, "methodToControllerObject");
        assertEquals(controller, methodToControllerObject.get(method));

        Map<String, Map<RequestMethod, Map.Entry<Method, Map<Integer, PathVariableModel>>>> routePathToMethod = (Map<String, Map<RequestMethod, Map.Entry<Method, Map<Integer, PathVariableModel>>>>) ReflectionUtil.getFieldAnyway(router, "routePathToMethod");
        assertNotNull(routePathToMethod.get("/models"));
        assertNotNull(routePathToMethod.get("/models").get(RequestMethod.GET));
        assertNotNull(routePathToMethod.get("/models/@{}").get(RequestMethod.GET));
        assertEquals(2, routePathToMethod.get("/models").size());
        assertEquals(3, routePathToMethod.get("/models/@{}").size());
    }

    @Test
    void route() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, FailedRouteException, FailedConvertJsonException {
        Router router = (Router) ReflectionUtil.newInstanceAnyway(Router.class);
        TestController controller = new TestController();
        router.invokeAfterControllerCreation(controller);

        // dummy api routing test
        assertEquals("getAll", router.route("/models", RequestMethod.GET, null, Vendor.UNKNOWN.name()).getMessage());

        // real work api test.
        assertThrows(InvocationTargetException.class, () -> router.route("/models", RequestMethod.POST, "{\"id\": null, \"name\": \"aaaa\"}", Vendor.UNKNOWN.name()).getMessage());
        assertNull(router.route("/models", RequestMethod.POST, "{\"id\": 1, \"name\": \"aaaa\"}", Vendor.UNKNOWN.name()).getMessage());
        assertNull(router.route("/models/@1", RequestMethod.PUT, "{\"name\": \"bbbb\"}", Vendor.UNKNOWN.name()).getMessage());
        assertEquals("bbbb", controller.models.get(1).name);
        assertThrows(InvocationTargetException.class, () -> router.route("/models/@3", RequestMethod.PUT, "{\"name\": \"cccc\"}", Vendor.UNKNOWN.name()).getMessage());
        assertEquals("bbbb", controller.models.get(1).name);

        // @ResponseBody
        assertEquals(null, router.route("/models/@5", RequestMethod.GET, null, Vendor.UNKNOWN.name()).getMessage());
        String expectedResponseBody = JacksonUtil.getInstance().toJson(controller.models.get(1));
        assertEquals(expectedResponseBody, router.route("/models/@1", RequestMethod.GET, null, Vendor.UNKNOWN.name()).getMessage());

    }
}

@Controller
@RequestMapping("/models")
class TestController {

    public Map<Integer, Model> models;

    public TestController() {
        models = new HashMap<>();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void post(@RequestBody Model model) throws FailedPostException {
        if (model.getId() == null || models.containsKey(model.getId())) {
            throw new FailedPostException("");
        }
        models.put(model.getId(), model);
    }

    @RequestMapping(value = "/@{id}", method = RequestMethod.PUT)
    public void put(@PathVariable("id") Integer id, @RequestBody Model model) throws FailedPutException {
        if (id == null || !models.containsKey(id)) {
            throw new FailedPutException("");
        }
        model.id = id;
        models.put(id, model);
    }

    @RequestMapping(value = "/@{id}", method = RequestMethod.GET)
    public @ResponseBody Model get(@PathVariable("id") Integer id, String noBound) throws FailedGetException, FailedConvertJsonException {
        return models.get(id);
    }

    // getAll : dummy api for routing test
    @RequestMapping(method = RequestMethod.GET)
    public String getAll() throws FailedGetException, FailedConvertJsonException {
        return "getAll";
    }

    // delete : dummy api for routing test
    @RequestMapping(value = "/@{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Integer id, String noBound) throws FailedDeleteException {
        return "deleteDone" + id + noBound;
    }
}

class Model {
    public Integer id;
    public String name;

    @ConstructorProperties({"id", "name"})
    public Model(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}