package com.woowahan.util.collection;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Jaeseong on 2021/04/11
 * Git Hub : https://github.com/AnJaeSeongS2
 */
@SuppressWarnings("unchecked")
public class ClassWithPriorityTest {

    @Test
    void compareTest() {
        List list = new ArrayList();
        ClassWithPriority sortTestElem = new ClassWithPriority(1);
        list.add(new ClassWithPriority(3));
        list.add(sortTestElem);
        list.add(new ClassWithPriority(2));

        assertEquals(sortTestElem, list.get(1));
        Collections.sort(list);
        assertEquals(sortTestElem, list.get(0));
    }
}
