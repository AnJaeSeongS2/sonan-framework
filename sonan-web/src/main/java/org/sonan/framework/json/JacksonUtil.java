package org.sonan.framework.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sonan.framework.json.throwable.FailedConvertJsonException;

import java.util.Collection;

/**
 * Jackson 을 사용한 convertUtil이다.
 *
 * Created by Jaeseong on 2021/04/07
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class JacksonUtil implements JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static JacksonUtil instance = new JacksonUtil();

    public static JacksonUtil getInstance() {
        return instance;
    }

    private JacksonUtil() {
    }

    /**
     * Model Object -> Json String.
     * @param srcObject
     * @return
     */
    @Override
    public String toJson(Object srcObject) throws FailedConvertJsonException {
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
    @Override
    public <M> M fromJson(String srcJson, Class<M> targetClassForConvert) throws FailedConvertJsonException {
        try {
            return mapper.readValue(srcJson, targetClassForConvert);
        } catch (Exception e) {
            throw new FailedConvertJsonException(String.format("Tried %s -> Object", srcJson), e);
        }
    }

    /**
     * Json String -> Model Object.
     * @param srcJson
     * @param targetCollectionClassForConvert
     * @param targetMemberClassForConvert
     * @return
     */
    @Override
    public <C extends Collection, M> C fromJson(String srcJson, Class<C> targetCollectionClassForConvert, Class<M> targetMemberClassForConvert) throws FailedConvertJsonException {
        try {
            return mapper.readValue(srcJson, mapper.getTypeFactory().constructCollectionType(targetCollectionClassForConvert, targetMemberClassForConvert));
        } catch (Exception e) {
            throw new FailedConvertJsonException(String.format("Tried %s -> Object", srcJson), e);
        }
    }
}
