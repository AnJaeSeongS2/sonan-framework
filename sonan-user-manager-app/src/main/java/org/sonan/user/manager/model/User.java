package org.sonan.user.manager.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.sonan.framework.web.annotation.model.IdAutoChangeableIfExists;
import org.sonan.framework.web.model.id.DefaultIntegerIdAutoIncrementModel;

import java.beans.ConstructorProperties;

/**
 * User 모델.
 * Model Id는 Integer id field이다.
 * id필드가 곂친 것이 insert시도되면, AutoIncrement된다.
 *
 * Created by Jaeseong on 2021/04/06
 * Git Hub : https://github.com/AnJaeSeongS2
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends DefaultIntegerIdAutoIncrementModel {

    @IdAutoChangeableIfExists
    private Integer id;
    private String name;
    private String address;

    @ConstructorProperties({"id", "name", "address"})
    public User(Integer id, String name, String address) {
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
        if (target.getClass() != this.getClass() || !(target instanceof User))
            return false;
        User targetUser = (User) target;
        return this.id == targetUser.id && this.name.equals(targetUser.name) && this.address.equals(targetUser.address);
    }
}
