package org.sonan.framework.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.junit.jupiter.api.Assertions;
import org.sonan.framework.json.throwable.FailedConvertJsonException;
import org.sonan.framework.web.annotation.model.Id;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Jaeseong on 2021/04/07
 * Git Hub : https://github.com/AnJaeSeongS2
 */
@SuppressWarnings("unchecked")
class JacksonUtilTest {

    @Test
    void fromJson() throws FailedConvertJsonException {
        TestClassValid obj = JacksonUtil.getInstance().fromJson("{\n" +
                "   \"id\":3,\n" +
                "   \"name\":\"name3\",\n" +
                "   \"otherField2\":\"address3\",\n" +
                "   \"otherField\":\"noRemain\"\n" +
                "}", TestClassValid.class);
        assertEquals(new TestClassValid(3, "name3"), obj);
        Assertions.assertDoesNotThrow(() -> JacksonUtil.getInstance().fromJson("{}", TestClassValid.class));
        assertThrows(FailedConvertJsonException.class, () -> JacksonUtil.getInstance().fromJson("{}", TestClassNoConstructorProperties.class));
        assertThrows(FailedConvertJsonException.class, () -> JacksonUtil.getInstance().fromJson("{adsf}", TestClassValid.class));
        assertThrows(FailedConvertJsonException.class, () -> JacksonUtil.getInstance().fromJson("{bbbcxs}", TestClassNoConstructorProperties.class));

        // List test.
        List<TestClassValid> objList = JacksonUtil.getInstance().fromJson(
                "[{\n" +
                "   \"id\":3,\n" +
                "   \"name\":\"name3\",\n" +
                "   \"otherField2\":\"address3\",\n" +
                "   \"otherField\":\"noRemain\"\n" +
                "}," +
                "{\n" +
                        "   \"id\":4,\n" +
                        "   \"name\":\"name4\",\n" +
                        "   \"otherField2\":\"address3\",\n" +
                        "   \"otherField\":\"noRemain\"\n" +
                        "}]"
                , ArrayList.class, TestClassValid.class);
        assertEquals(Lists.newArrayList(new TestClassValid(3, "name3"),new TestClassValid(4, "name4")), objList);
    }

    @Test
    void toJson() throws FailedConvertJsonException {
        TestClassValid obj = new TestClassValid(2, "name2");
        String result = JacksonUtil.getInstance().toJson(obj);
        assertEquals("\"testStringOnly\"", JacksonUtil.getInstance().toJson("testStringOnly"));
        assertEquals("\"[]\"", JacksonUtil.getInstance().toJson("[]"));
        assertEquals("\"1234\"", JacksonUtil.getInstance().toJson("1234"));
        TestClassValid otherUserFromSameResource = JacksonUtil.getInstance().fromJson(result, TestClassValid.class);
        assertEquals(obj, otherUserFromSameResource);

        // List test.
        List<TestClassValid> objList = Lists.newArrayList(new TestClassValid(3, "name3"),new TestClassValid(4, "name4"));
        String strFromObjList = JacksonUtil.getInstance().toJson(objList);
        assertEquals(Lists.newArrayList(new TestClassValid(3, "name3"),new TestClassValid(4, "name4")),
                JacksonUtil.getInstance().fromJson(strFromObjList, ArrayList.class, TestClassValid.class));
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class TestClassValid {

    @Id
    private Integer id;
    private String name;

    @ConstructorProperties({"id", "name"})
    public TestClassValid(Integer id, String name) {
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
        if (target.getClass() != this.getClass() || !(target instanceof TestClassValid))
            return false;
        TestClassValid targetUser = (TestClassValid) target;
        return this.id == targetUser.id && this.name.equals(targetUser.name);
    }
}


@JsonIgnoreProperties(ignoreUnknown = true)
class TestClassNoConstructorProperties {

    @Id
    private Integer id;
    private String name;

    @ConstructorProperties({"id"})
    public TestClassNoConstructorProperties(Integer id, String name) {
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
        if (target.getClass() != this.getClass() || !(target instanceof TestClassNoConstructorProperties))
            return false;
        TestClassNoConstructorProperties targetUser = (TestClassNoConstructorProperties) target;
        return this.id == targetUser.id && this.name.equals(targetUser.name);
    }
}
