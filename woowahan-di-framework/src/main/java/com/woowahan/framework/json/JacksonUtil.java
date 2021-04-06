package com.woowahan.framework.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowahan.framework.json.throwable.FailedConvertJsonException;

/**
 * Jackson 을 사용한 convertUtil이다.
 *
 * Created by Jaeseong on 2021/04/07
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class JacksonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Model Object -> Json String.
     * @param srcObject
     * @return
     */
    public static <T> String toJson(T srcObject) throws FailedConvertJsonException {
        try {
            return mapper.writeValueAsString(srcObject);
        } catch (Exception e) {
            throw new FailedConvertJsonException(String.format("Tried %s -> Json", srcObject), e);
        }
    }

    /**
     * Json String -> Model Object.
     * @param srcJson
     * @param targetClassForConvert
     * @return
     */
    public static <T> T fromJson(String srcJson, Class<T> targetClassForConvert) throws FailedConvertJsonException {
        try {
            return mapper.readValue(srcJson, targetClassForConvert);
        } catch (Exception e) {
            throw new FailedConvertJsonException(String.format("Tried %s -> Object", srcJson), e);
        }
    }
}
