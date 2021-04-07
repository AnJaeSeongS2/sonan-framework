package com.woowahan.framework.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.woowahan.framework.web.annotation.model.Id;

import java.beans.ConstructorProperties;


/**
 * Created by Jaeseong on 2021/04/07
 * Git Hub : https://github.com/AnJaeSeongS2
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestClass {

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
