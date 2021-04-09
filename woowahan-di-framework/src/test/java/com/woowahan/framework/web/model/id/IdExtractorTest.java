package com.woowahan.framework.web.model.id;

import com.woowahan.framework.web.annotation.model.IdAutoChangeableIfExists;
import com.woowahan.framework.web.annotation.model.Id;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Jaeseong on 2021/04/07
 * Git Hub : https://github.com/AnJaeSeongS2
 */
class IdExtractorTest {
    static TestModel model;
    static TestModelNoId modelNoId;

    @BeforeAll
    static void initTestModel() {
        model = new TestModel("aaa", "name10");
        modelNoId = new TestModelNoId("bbb", "nameNoId");
    }

    @Test
    void getId() {
        assertEquals("aaa", IdExtractor.getId(model));
        assertEquals(modelNoId.hashCode(), IdExtractor.getId(modelNoId));
    }

    @Test
    void getIdClass() {
        assertEquals(String.class, IdExtractor.getIdClass(TestModel.class));
        assertEquals(Integer.class, IdExtractor.getIdClass(TestModelNoId.class));
    }

    @Test
    void getIdField() throws NoSuchFieldException {
        TestModel2 model2 = new TestModel2(1, "2");
        assertEquals(IdAutoChangeableIfExists.class, IdExtractor.getIdField(model2).getAnnotation(IdAutoChangeableIfExists.class).annotationType());
    }

    @Test
    void getIdAndField() throws NoSuchFieldException, IllegalAccessException {
        TestModel2 model2 = new TestModel2(1, "2");
        assertEquals(1, IdExtractor.getIdAndField(model2).getKey());
        assertEquals(IdAutoChangeableIfExists.class, IdExtractor.getIdAndField(model2).getValue().getAnnotation(IdAutoChangeableIfExists.class).annotationType());
    }
}

class TestModel2 {

    @IdAutoChangeableIfExists
    private Integer id;
    public String name;

    public TestModel2(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}

class TestModel {

    @Id
    private String id;
    public String name;

    public TestModel(String id, String name) {
        this.id = id;
        this.name = name;
    }
}

class TestModelNoId {

    private String noId;
    public String name;

    public TestModelNoId(String noId, String name) {
        this.noId = noId;
        this.name = name;
    }
}