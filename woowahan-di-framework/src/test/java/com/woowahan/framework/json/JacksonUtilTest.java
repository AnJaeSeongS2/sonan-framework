package com.woowahan.framework.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.woowahan.framework.json.throwable.FailedConvertJsonException;
import com.woowahan.framework.web.annotation.model.Id;
import org.junit.jupiter.api.Test;

import java.beans.ConstructorProperties;

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

@JsonIgnoreProperties(ignoreUnknown = true)
class TestClass {

    @Id
    private Integer id;
    private String name;

    @ConstructorProperties({"id", "name"})
    public TestClass(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public boolean equals(Object target) {
        if (target == null)
            return false;
        if (target.getClass() != this.getClass() || !(target instanceof TestClass))
            return false;
        TestClass targetShop = (TestClass) target;
        return this.id == targetShop.id && this.name.equals(targetShop.name);
    }
}