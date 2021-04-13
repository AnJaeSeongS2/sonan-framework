package org.sonan.framework.context.bean;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Jaeseong on 2021/04/01
 * Git Hub : https://github.com/AnJaeSeongS2
 */
class BeanIdentifierTest {

    @Test
    void testToString() {
        assertEquals("java.lang.String:aaaa", new BeanIdentifier("java.lang.String", "aaaa").toString());
        assertEquals("java.lang.String:", new BeanIdentifier("java.lang.String", null).toString());
        assertEquals("java.lang.String:aaaa", new BeanIdentifier("java.lang.String", "aaaa").genId());
        assertEquals("java.lang.String:", new BeanIdentifier("java.lang.String", null).genId());
    }

    @Test
    void testEquals() {
        assertEquals(true, new BeanIdentifier("java.lang.String", "aaaa").equals(new BeanIdentifier("java.lang.String", "aaaa")));
        assertEquals(true, new BeanIdentifier("java.lang.String", null).equals(new BeanIdentifier("java.lang.String", null)));

        assertEquals(false, new BeanIdentifier("java.lang.Integer", "aaaa").equals(new BeanIdentifier("java.lang.String", "aaaa")));
        assertEquals(false, new BeanIdentifier("java.lang.String", "aaaa").equals(new BeanIdentifier("java.lang.String", "bbbb")));
        assertEquals(false, new BeanIdentifier("java.lang.String", "aaaa").equals(new BeanIdentifier("java.lang.String", null)));
    }
}