package org.sonan.framework.json;

import org.sonan.framework.json.throwable.FailedConvertJsonException;

import java.util.Collection;

/**
 * TODO: json-support 모듈을 별도로 생성하고, 타 vendor 구현체를 사용하는 방식은 spi를 따른다.
 * TODO: default spi 구현체는 JacksonUtil일 것.
 * TODO: 고객은 app 작성시 vendor 상관없이 framework가 제공하는 annotation, util api만 call하게끔 할 것.
 *
 * Json 관련 타lib vendor를 지원한다면, vendor별로 이 JsonUtil interface를 구현해야 한다.
 *
 * Created by Jaeseong on 2021/04/07
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public interface JsonUtil {

    /**
     * Model Object-> Json String.
     * @param srcObject
     * @return
     */
    String toJson(Object srcObject) throws FailedConvertJsonException;

    /**
     * Json String -> Model Object.
     * @param srcJson
     * @param targetClassForConvert
     * @return
     */
    <M> M fromJson(String srcJson, Class<M> targetClassForConvert) throws FailedConvertJsonException;

    /**
     * Json String -> Model Object.
     * @param srcJson
     * @param targetCollectionClassForConvert
     * @param targetMemberClassForConvert
     * @return
     */
    <C extends Collection, M> C fromJson(String srcJson, Class<C> targetCollectionClassForConvert, Class<M> targetMemberClassForConvert)  throws FailedConvertJsonException;
}
