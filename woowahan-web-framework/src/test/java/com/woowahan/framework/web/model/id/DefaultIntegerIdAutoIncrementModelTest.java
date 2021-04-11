package com.woowahan.framework.web.model.id;

import com.woowahan.framework.web.annotation.model.IdAutoChangeableIfExists;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Jaeseong on 2021/04/09
 * Git Hub : https://github.com/AnJaeSeongS2
 */
class DefaultIntegerIdAutoIncrementModelTest {

    @Test
    void changeIdAuto() throws FailedIdAutoChangeException {
        AutoIncrementModel model = new AutoIncrementModel(1, "test");
        assertEquals(2, model.changeIdAuto());
        assertEquals(2, model.getId());
    }
}



class AutoIncrementModel extends DefaultIntegerIdAutoIncrementModel {
    @IdAutoChangeableIfExists
    private Integer id;
    public String name;

    public AutoIncrementModel(Integer id, String name) {
        this.id = id;
        this.name = name;
    }


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}