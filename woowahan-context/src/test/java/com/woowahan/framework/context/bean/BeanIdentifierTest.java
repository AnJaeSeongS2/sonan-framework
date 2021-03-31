package com.woowahan.framework.context.bean;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Jaeseong on 2021/04/01
 * Git Hub : https://github.com/AnJaeSeongS2
 */
class BeanIdentifierTest {

    @Test
    void testToString() {
        assertEquals("java.lang.String:aaaa", new BeanIdentifier(String.class, "aaaa").toString());
        assertEquals("java.lang.String:", new BeanIdentifier(String.class, null).toString());
        assertEquals("java.lang.String:aaaa", new BeanIdentifier(String.class, "aaaa").genId());
        assertEquals("java.lang.String:", new BeanIdentifier(String.class, null).genId());
    }

    @Test
    void testEquals() {
        assertEquals(true, new BeanIdentifier(String.class, "aaaa").equals(new BeanIdentifier(String.class, "aaaa")));
        assertEquals(true, new BeanIdentifier(String.class, null).equals(new BeanIdentifier(String.class, null)));

        assertEquals(false, new BeanIdentifier(Integer.class, "aaaa").equals(new BeanIdentifier(String.class, "aaaa")));
        assertEquals(false, new BeanIdentifier(String.class, "aaaa").equals(new BeanIdentifier(String.class, "bbbb")));
        assertEquals(false, new BeanIdentifier(String.class, "aaaa").equals(new BeanIdentifier(String.class, null)));
    }
}