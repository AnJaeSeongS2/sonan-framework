package org.sonan.framework.web;

import org.sonan.framework.context.annotation.Service;
import org.sonan.framework.context.bean.lifecycle.ControllerLifecycleInvocation;
import org.sonan.framework.json.JacksonUtil;
import org.sonan.framework.web.annotation.*;
import org.sonan.framework.web.protocol.RequestMessage;
import org.sonan.framework.web.protocol.ResponseMessage;
import org.sonan.framework.web.throwable.FailedRouteException;
import org.sonan.framework.web.util.UrlUtil;
import org.sonan.logback.support.Markers;
import org.sonan.util.annotation.Nullable;
import org.sonan.util.reflect.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Router도 @Service이다.
 * Router BigO(1)로 처리하기 위해, request 날리는 측이 PathVariable임을 알려줄 수 있게끔 api개선이 이루어졌다.
 *
 * 요청이 a/@{b1 URLEncoded}@{b2 URLEncoded}/c/d/@{e URLEncoded}.GET 이라면,
 * variable 부분을 variable[]로 정리해둔다.
 * a/@{}@{}/c/d/@{} routePath를 찾아내 GET에 해당하는 Method와 pathVariableName[]를 찾는다.
 * 찾은 것에서 pathVariableName[] 을 통해 pathVariableName을 알아낸다.
 * 찾은 Method를 call할 때, pathVariableName 기반으로 variable String을 paramType에 맞게 casting해서 inject해준다.
 *
 * !!!! bigO 를 1로 하기위해 요구사항중 localhost:8080/users/1 이 아닌 localhost:8080/users/@1 로 수정해 진행한다. !!!!
 * Created by Jaeseong on 2021/04/07
 * Git Hub : https://github.com/AnJaeSeongS2
 */

//TODO: 직접 Method.invoke 하는 것을 ReflectionUtil 사용하게끔
@SuppressWarnings("unchecked")
@Service
public class Router implements ControllerLifecycleInvocation {
    private static final Logger logger = LoggerFactory.getLogger(Router.class);

    // @Controller가 bean 생성될 때, 자동으로 routePathToMethod에 자동 등록된다. ! 따라서 Controller 기반 빈 생성이 완료돼야 Route처리가 정상 진행 가능하다.
    // routePathToMethod same as Map<routePath, Map<RequestMethod, Map.Entry<RoutedMethod, Map<paramIndex, PathVariableModel>>>>
    private Map<String, Map<RequestMethod, Map.Entry<Method, Map<Integer, PathVariableModel>>>> routePathToMethod;
    private Map<Method, Object> methodToControllerObject;

    public Router() {
        routePathToMethod = new ConcurrentHashMap<>();
        methodToControllerObject = new ConcurrentHashMap<>();
    }
    /**
     * route처리 돼서 매핑돼있는 Method가 invoke된다.
     *
     * @see RequestMessage
     * @see ResponseMessage
     * @param requestMessage
     * @throws FailedRouteException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public ResponseMessage route(RequestMessage requestMessage) throws FailedRouteException, InvocationTargetException, IllegalAccessException {
        return route(requestMessage.getUrl(), RequestMethod.valueOf(requestMessage.getRequestMethod()), String.valueOf(requestMessage.getMessage()), requestMessage.getVendor());
    }

    /**
     * route처리 돼서 매핑돼있는 Method가 invoke된다.
     *
     * @see org.sonan.framework.web.protocol.Vendor
     * @param url
     * @param requestMethod
     * @param vendor
     * @throws FailedRouteException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public ResponseMessage route(String url, RequestMethod requestMethod, @Nullable String requestBody, String vendor) throws FailedRouteException, InvocationTargetException, IllegalAccessException {
        Map.Entry<String, String[]> routePathAndPathVariables = UrlUtil.genRoutePathAndPathVariablesFromUrl(url);
        Map<RequestMethod, Map.Entry<Method, Map<Integer, PathVariableModel>>> methodMap = routePathToMethod.get(routePathAndPathVariables.getKey());
        if (methodMap == null) {
            throw new FailedRouteException(String.format("cannot found route about this url. url : %s, requestMethod : %s", url, requestMethod.name()));
        }

        Map.Entry<Method, Map<Integer, PathVariableModel>> methodAndPathVariableModelMap = methodMap.get(requestMethod);
        if (methodAndPathVariableModelMap == null) {
            throw new FailedRouteException(String.format("cannot found route about this url. url : %s, requestMethod : %s", url, requestMethod.name()));
        }

        // start Binding param values.
        Method routedMethod = methodAndPathVariableModelMap.getKey();
        Parameter[] params = routedMethod.getParameters();
        List<Object> paramBound = new ArrayList<>();
        for (int i = 0; i < params.length; i++) {
            if (methodAndPathVariableModelMap.getValue().containsKey(i)) {
                int indexOnUrl = methodAndPathVariableModelMap.getValue().get(i).getVariableIndexOnUrl();
                Object injectedObject = routePathAndPathVariables.getValue()[indexOnUrl];
                try {
                    injectedObject = ReflectionUtil.invokeStaticMethod(params[i].getType(), "valueOf", new Class[]{String.class}, injectedObject);
                } catch (Exception e) {
                    if (logger.isDebugEnabled(Markers.MESSAGE.get()))
                        logger.debug(Markers.MESSAGE.get(), String.format("cannot convert PathVariable String to %s. original Value : %s", params[i].getType(), injectedObject)); // 아직 convertedObject 는 original이다.
                }
                paramBound.add(injectedObject);
            } else {
                Object convertedObject = genConvertedObjectIfHasRequestBodyAnnotation(params[i], requestBody);
                paramBound.add(convertedObject);
            }
        }

        Object resultInvoked = routedMethod.invoke(methodToControllerObject.get(routedMethod), paramBound.toArray());
        return new ResponseMessage(vendor, genConvertedObjectIfHasResponseBodyAnnotation(routedMethod, resultInvoked));
    }

    /**
     * convertedObject 를 반환한다.
     *
     *
     * 이거, 다른 곳으로 옮겨야한다.
     * @RequestBody resolver로 옮긴다.
     *
     * @param param
     * @param requestBody
     * @return
     */
    private @Nullable Object genConvertedObjectIfHasRequestBodyAnnotation(Parameter param, @Nullable String requestBody) {
        if (requestBody == null || param.getAnnotation(RequestBody.class) == null)
            return requestBody;
        try {
            // @RequestBody 이므로, requestBody String value를 Jsonutil로 Object화 해준다.
            return JacksonUtil.getInstance().fromJson(requestBody, param.getType());
        } catch (Exception e) {
            // 회원정보일 수 있으므로 정보 자체를 찍으면 안된다.
            if (logger.isWarnEnabled())
                logger.warn(String.format("Failed convert requestBody to Object (by @RequestBody). so, return null. objectType: %s", param.getType()), e);
            return requestBody;
        }
    }

    /**
     * convertedObject를 반환한다.
     *
     * @param method
     * @param resultInvoked
     * @return
     */
    private @Nullable Object genConvertedObjectIfHasResponseBodyAnnotation(Method method, @Nullable Object resultInvoked) {
        if (resultInvoked == null || method.getAnnotation(ResponseBody.class) == null)
            return resultInvoked;
        try {
            // @ResponseBody 이므로, Return value를 JsonUtil로 jsonString화 해준다.
            return JacksonUtil.getInstance().toJson(resultInvoked);
        } catch (Exception e) {
            // 회원정보일 수 있으므로 정보 자체를 찍으면 안된다.
            if (logger.isWarnEnabled())
                logger.warn(String.format("Failed convert result to JsonString (by @ResponseBody). so, return original result. Class: %s, Method: %s", methodToControllerObject.get(method), method));
            return resultInvoked;
        }
    }

    /**
     * Route에 RequestMapping method가 등록된다.
     * @see org.sonan.framework.context.annotation.Controller 에 대한 bean이 framework 기동 타이밍에 자동 한다.
     * @param urlOnRequestMapping example: a/b/@{id}@{id2}/d/e/@{id3}
     */
    public void register(String urlOnRequestMapping, RequestMethod[] requestMethods, Method method, Object controller) {
        // routePathAndPathVariableNames same as Map<routePath, Map<RequestMethod, Map.Entry<Method, pathVariableName[]>>>
        Map.Entry<String, String[]> routePathAndPathVariableNames = UrlUtil.genRoutePathAndPathVariableNamesFromUrlOnRequestMapping(urlOnRequestMapping);

        Map<Integer, PathVariableModel> pathVariableModelMap = new ConcurrentHashMap<>();
        Class<?>[] paramClasses = method.getParameterTypes();
        Annotation[][] paramAnnos =  method.getParameterAnnotations();
        for (int i = 0; i < paramClasses.length; i++) {
            for (Annotation annotation : paramAnnos[i]) {
                if (annotation instanceof PathVariable) {
                    String variableName = ((PathVariable) annotation).value();
                    PathVariableModel model = new PathVariableModel(i, method, variableName, paramClasses[i], getSearchedIndex(variableName, routePathAndPathVariableNames.getValue()));
                    pathVariableModelMap.put(i, model);
                }
            }
        }
        Map<RequestMethod, Map.Entry<Method, Map<Integer, PathVariableModel>>> requestMethodMap = routePathToMethod.containsKey(routePathAndPathVariableNames.getKey()) ? routePathToMethod.get(routePathAndPathVariableNames.getKey()) : new ConcurrentHashMap<>();
        for (RequestMethod requestMethod : requestMethods) {
            Object prev = requestMethodMap.put(requestMethod, new AbstractMap.SimpleEntry<>(method, pathVariableModelMap));
            if (prev != null) {
                if (logger.isTraceEnabled(Markers.MESSAGE.get()))
                    logger.trace(Markers.MESSAGE.get(), String.format("route mapping method is changed. reqeustMethod : %s, routePath : %s", requestMethod.name(), routePathAndPathVariableNames.getKey()));
            }
        }

        routePathToMethod.put(routePathAndPathVariableNames.getKey(), requestMethodMap);
        methodToControllerObject.put(method, controller);
    }

    private int getSearchedIndex(String searchVariableName, String[] variableNames) {
        for (int i = 0; i < variableNames.length; i++) {
            if (searchVariableName.equals(variableNames[i]))
                return i;
        }
        return -1;
    }

    /**
     * Controller Bean들이 생성될 때 이 method가 호출된다.
     * @param createdController
     */
    @Override
    public void invokeAfterControllerCreation(Object createdController) {
        String url = UrlUtil.PATH_SEPARATOR;
        RequestMapping clazzMapping = ReflectionUtil.getAnnotation(createdController, RequestMapping.class);
        if (clazzMapping == null) {
            if (logger.isTraceEnabled(Markers.LIFE_CYCLE.get()))
                logger.trace(Markers.LIFE_CYCLE.get(), String.format("class's RequestMapping is no exists. class : %s", createdController.getClass()));
        } else {
            url += (genPathWithoutSeparator(clazzMapping.value()));
        }

        for (Method method : createdController.getClass().getMethods()) {
            RequestMapping methodMapping = ReflectionUtil.getAnnotation(method, RequestMapping.class);
            if (methodMapping == null) {
                if (logger.isTraceEnabled(Markers.LIFE_CYCLE.get()))
                    logger.trace(Markers.LIFE_CYCLE.get(), String.format("method's RequestMapping is no exists. method : %s", method));
                continue;
            }
            String appendElem = genPathWithoutSeparator(methodMapping.value());
            String currentUrlOnMapping = url;
            if (appendElem != null && appendElem.length() > 0) {
                currentUrlOnMapping += UrlUtil.PATH_SEPARATOR + appendElem;
            }
            register(currentUrlOnMapping, methodMapping.method(), method, createdController);
        }
    }

    private String genPathWithoutSeparator(String src) {
        int substringStart = 0;
        int substringEnd = src.length();
        if (src.length() != 0 && UrlUtil.PATH_SEPARATOR.charAt(0) == src.charAt(0)) {
            substringStart = 1;
        }
        if (src.length() != 0 && UrlUtil.PATH_SEPARATOR.charAt(0) == src.charAt(src.length() - 1)) {
            substringEnd = src.length() - 1;
        }
        if (substringStart >= substringEnd) {
            return "";
        }
        return src.substring(substringStart, substringEnd);
    }
}
