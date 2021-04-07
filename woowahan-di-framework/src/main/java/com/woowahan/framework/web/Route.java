package com.woowahan.framework.web;

import com.woowahan.framework.context.annotation.Service;
import com.woowahan.framework.context.bean.lifecycle.ControllerLifecycleInvocation;
import com.woowahan.framework.web.annotation.PathVariable;
import com.woowahan.framework.web.annotation.RequestMapping;
import com.woowahan.framework.web.annotation.RequestMethod;
import com.woowahan.framework.web.throwable.FailedRouteException;
import com.woowahan.framework.web.util.UrlUtil;
import com.woowahan.logback.support.Markers;
import com.woowahan.util.reflect.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Route도 @Service이다.
 * Route BigO(1)로 처리하기 위해, request 날리는 측이 PathVariable임을 알려줄 수 있게끔 api개선이 이루어졌다.
 *
 * 요청이 a/#(b1 URLEncoded)#(b2 URLEncoded)/c/d/#(e URLEncoded).GET 이라면,
 * variable 부분을 variable[]로 정리해둔다.
 * a/#{}#{}/c/d/#{} routePath를 찾아내 GET에 해당하는 Method와 pathVariableName[]를 찾는다.
 * 찾은 것에서 pathVariableName[] 을 통해 pathVariableName을 알아낸다.
 * 찾은 Method를 call할 때, pathVariableName 기반으로 variable String을 paramType에 맞게 casting해서 inject해준다.
 *
 * !!!! bigO 를 1로 하기위해 요구사항중 localhost:8080/shops/1 이 아닌 localhost:8080/shops/#1 로 수정해 진행한다. !!!!
 * Created by Jaeseong on 2021/04/07
 * Git Hub : https://github.com/AnJaeSeongS2
 */

@Service
public class Route implements ControllerLifecycleInvocation {
    private static final Logger logger = LoggerFactory.getLogger(Route.class);

    // @Controller가 bean 생성될 때, 자동으로 routePathToMethod에 자동 등록된다. ! 따라서 Controller 기반 빈 생성이 완료돼야 Route처리가 정상 진행 가능하다.
    // routePathToMethod same as Map<routePath, Map<RequestMethod, Map.Entry<RoutedMethod, Map<paramIndex, PathVariableModel>>>>
    private Map<String, Map<RequestMethod, Map.Entry<Method, Map<Integer, PathVariableModel>>>> routePathToMethod;
    private Map<Method, Object> methodToControllerObject;

    /**
     * 이 클래스는 외부에서 직접 생성할 수 없다.
     */
    protected Route() {
        routePathToMethod = new ConcurrentHashMap<>();
        methodToControllerObject = new ConcurrentHashMap<>();
    }

    /**
     * route처리 돼서 매핑돼있는 Method가 invoke된다.
     *
     * @param url
     * @param requestMethod
     * @throws FailedRouteException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public Object route(String url, RequestMethod requestMethod) throws FailedRouteException, InvocationTargetException, IllegalAccessException {
        Map.Entry<String, String[]> routePathAndPathVariables = UrlUtil.genRoutePathAndPathVariablesFromUrl(url);
        Map<RequestMethod, Map.Entry<Method, Map<Integer, PathVariableModel>>> methodMap = routePathToMethod.get(routePathAndPathVariables.getKey());
        if (methodMap == null) {
            throw new FailedRouteException(String.format("cannot found route about this url : %s, this requestMethod : %s", url, requestMethod.name()));
        }

        Map.Entry<Method, Map<Integer, PathVariableModel>> methodAndPathVariableModelMap = methodMap.get(requestMethod);
        if (methodAndPathVariableModelMap == null) {
            throw new FailedRouteException(String.format("cannot found route about this url : %s, this requestMethod : %s", url, requestMethod.name()));
        }

        Method routedMethod = methodAndPathVariableModelMap.getKey();
        Class<?>[] paramClasses = routedMethod.getParameterTypes();
        List<Object> paramBound = new ArrayList<>();

        for (int i = 0; i < paramClasses.length; i++) {
            if (methodAndPathVariableModelMap.getValue().containsKey(i)) {
                int indexOnUrl = methodAndPathVariableModelMap.getValue().get(i).getVariableIndexOnUrl();
                Object convertedObject = routePathAndPathVariables.getValue()[indexOnUrl];
                try {
                    convertedObject = ReflectionUtil.invokeStaticMethod(paramClasses[i], "valueOf", new Class[]{String.class}, convertedObject);
                } catch (Exception e) {
                    if (logger.isDebugEnabled())
                        logger.debug(Markers.MESSAGE.get(), String.format("cannot convert PathVariable String to %s. original Value : %s", paramClasses[i], convertedObject)); // 아직 convertedObject 는 original이다.
                }
                paramBound.add(convertedObject);
            } else {
                paramBound.add(null);
            }
        }
        return routedMethod.invoke(methodToControllerObject.get(routedMethod), paramBound.toArray());
    }


    /**
     * Route에 RequestMapping method가 등록된다.
     * @see com.woowahan.framework.context.annotation.Controller 에 대한 bean이 framework 기동 타이밍에 자동 한다.
     * @param urlOnRequestMapping example: a/b/#{id}#{id2}/d/e/#{id3}
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
                if (logger.isTraceEnabled())
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
            if (logger.isTraceEnabled())
                logger.trace(Markers.LIFE_CYCLE.get(), String.format("class's RequestMapping is no exists. class : %s", createdController.getClass()));
        } else {
            url += (genPathWithoutSeparator(clazzMapping.value()));
        }

        for (Method method : createdController.getClass().getMethods()) {
            RequestMapping methodMapping = ReflectionUtil.getAnnotation(method, RequestMapping.class);
            if (methodMapping == null) {
                if (logger.isTraceEnabled())
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
        return src.substring(substringStart, substringEnd);
    }
}
