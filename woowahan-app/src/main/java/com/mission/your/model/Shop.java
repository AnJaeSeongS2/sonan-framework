package com.mission.your.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.woowahan.framework.web.annotation.model.Id;

import java.beans.ConstructorProperties;

/**
 * Shop 모델.
 * Model Id는 Integer id field이다.
 *
 * Created by Jaeseong on 2021/04/06
 * Git Hub : https://github.com/AnJaeSeongS2
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Shop {

    @Id
    private Integer id;
    private String name;
    private String address;

    @ConstructorProperties({"id", "name", "address"})
    public Shop(Integer id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object target) {
        if (target == null)
            return false;
        if (target.getClass() != this.getClass() || !(target instanceof Shop))
            return false;
        Shop targetShop = (Shop) target;
        return this.id == targetShop.id && this.name.equals(targetShop.name) && this.address.equals(targetShop.address);
    }
}
