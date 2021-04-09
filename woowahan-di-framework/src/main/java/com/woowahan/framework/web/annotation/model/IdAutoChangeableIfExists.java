package com.woowahan.framework.web.annotation.model;

import com.woowahan.framework.web.model.id.IdAutoChanger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Model Class의 field에 지정하는 annotation
 * 해당 field가 있는 Class는 java.util.Comparator 구현체여야한다.
 * 해당 field가 있는 Class는 IdAutoSetter의 구현체여야한다. field의 id값이 X 이고 X에 해당하는 data가 이미 repo에 존재한다면, IdAutoSetter::handleIfAlreadyExists(Object inputModelTried)가 동작한다.
 * @see Comparable
 * @see IdAutoChanger
 *
 * @see java.util.Comparator
 *
 * Created by Jaeseong on 2021/04/06
 * Git Hub : https://github.com/AnJaeSeongS2
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IdAutoChangeableIfExists {
}
