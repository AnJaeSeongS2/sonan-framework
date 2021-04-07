package com.woowahan.framework.repository.memory;

import com.woowahan.framework.throwable.*;
import com.woowahan.framework.web.annotation.model.Id;
import com.woowahan.util.reflect.ReflectionUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Jaeseong on 2021/04/07
 * Git Hub : https://github.com/AnJaeSeongS2
 */
class GenericMapRepositoryTest {

    GenericMapRepository<TestModel> repo;

    @BeforeEach
    void initRepo() {
        repo = new GenericMapRepository<>();
    }

    @Test
    void post() throws FailedRestException, NoSuchFieldException, IllegalAccessException {
        assertThrows(FailedPostException.class, () -> repo.post(new TestModel(null, "name")));
        repo.post(new TestModel(1, "name1"));
        assertThrows(FailedPostException.class, () -> repo.post(new TestModel(1, "name")));
        repo.post(new TestModel(2, "name2"));
        Map<Object, TestModel> dataMap = (Map) ReflectionUtil.getFieldAnyway(repo,"dataMap");
        assertEquals("name1", dataMap.get(1).name);
        assertEquals("name2", dataMap.get(2).name);
    }

    @Test
    void put() throws FailedRestException, NoSuchFieldException, IllegalAccessException {
        repo.post(new TestModel(1, "name"));
        repo.put(new TestModel(1, "nameUpdated"));
        assertThrows(FailedPutException.class, () -> repo.put(new TestModel(2, "nameUpdated")));

        Map<Object, TestModel> dataMap = (Map) ReflectionUtil.getFieldAnyway(repo,"dataMap");

        // success updated.
        assertEquals("nameUpdated", dataMap.get(1).name);
    }

    @Test
    void delete() throws FailedRestException, NoSuchFieldException, IllegalAccessException {
        TestModel originalModel = new TestModel(1, "name");
        repo.post(originalModel);
        TestModel deletedModel = repo.delete(1);
        assertEquals(originalModel, deletedModel);


        Map<Object, TestModel> dataMap = (Map) ReflectionUtil.getFieldAnyway(repo,"dataMap");
        // already deleted.
        assertNull(dataMap.get(1));

        // no exists element about 2.
        assertThrows(FailedDeleteException.class, () -> repo.delete(2));
    }

    @Test
    void get() throws FailedRestException, NoSuchFieldException, IllegalAccessException {
        TestModel originalModel = new TestModel(1, "name");
        repo.post(originalModel);


        Map<Object, TestModel> dataMap = (Map) ReflectionUtil.getFieldAnyway(repo,"dataMap");
        assertEquals(originalModel, dataMap.get(1));

        // no exists element about 2.
        assertThrows(FailedGetException.class, () -> repo.get(2));
    }

    @Test
    void getAll() throws FailedRestException {
        TestModel originalModel = new TestModel(1, "name");
        TestModel originalModel2 = new TestModel(2, "name");
        repo.post(originalModel);
        repo.post(originalModel2);

        TestModel noAddedModel = new TestModel(3,"noAdded");

        List<TestModel> datas = repo.getAll();
        assertEquals(2, datas.size());
        assertTrue(datas.contains(originalModel));
        assertTrue(datas.contains(originalModel2));
        assertFalse(datas.contains(noAddedModel));
    }
}

class TestModel {

    @Id
    private Integer id;
    public String name;

    public TestModel(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}