package com.woowahan.framework.json;

import com.woowahan.framework.json.throwable.FailedConvertJsonException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Jaeseong on 2021/04/07
 * Git Hub : https://github.com/AnJaeSeongS2
 */
class JacksonUtilTest {

    @Test
    void fromJson() throws FailedConvertJsonException {
        TestClass obj = JacksonUtil.fromJson("{\n" +
                "   \"id\":3,\n" +
                "   \"name\":\"name3\",\n" +
                "   \"otherField2\":\"address3\",\n" +
                "   \"otherField\":\"noRemain\"\n" +
                "}", TestClass.class);
        assertEquals(new TestClass(3, "name3"), obj);
    }

    @Test
    void toJson() throws FailedConvertJsonException {
        TestClass obj = new TestClass(2, "name2");
        String result = JacksonUtil.toJson(obj);
        TestClass otherShopFromSameResource = JacksonUtil.fromJson(result, TestClass.class);
        assertEquals(obj, otherShopFromSameResource);
    }

}