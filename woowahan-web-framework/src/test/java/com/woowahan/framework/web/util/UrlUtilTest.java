package com.woowahan.framework.web.util;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.AbstractMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Jaeseong on 2021/04/07
 * Git Hub : https://github.com/AnJaeSeongS2
 */
class UrlUtilTest {

    @Test
    void encode() throws UnsupportedEncodingException {
        assertEquals("id1LNa%2F.q%2F%5D%60pj3p", UrlUtil.encode("id1LNa/.q/]`pj3p"));
    }

    @Test
    void decode() throws UnsupportedEncodingException {
        assertEquals("@id1LNa/.q/]`pj3p@@@@////...", UrlUtil.decode("@id1LNa%2F.q%2F%5D%60pj3p@%40%40%40%2F%2F%2F%2F..."));
    }

    @Test
    void genRoutePathAndPathVariablesFromUrl() {
        /**
         * @param url example: /a/@id1_URL_ENCODED@id2_URL_ENCODED/c/d/@id3_URL_ENCODED
         * @return example: { /a/@{}@{}/c/d/@{} , (decoded) [id1value, id2value, id3value]}
         */
        assertEquals("/a/@{}@{}/c/d/@{}", UrlUtil.genRoutePathAndPathVariablesFromUrl("/a/@id1LNa%2F.q%2F%5D%60pj3p@%40%40%40%2F%2F%2F%2F.../c/d/@%40%40%40%2F%2F%2F%2F...").getKey());
        assertArrayEquals(new String[]{"id1LNa/.q/]`pj3p", "@@@////...", "@@@////..."}, UrlUtil.genRoutePathAndPathVariablesFromUrl("/a/@id1LNa%2F.q%2F%5D%60pj3p@%40%40%40%2F%2F%2F%2F.../c/d/@%40%40%40%2F%2F%2F%2F...").getValue());
    }

    @Test
    void genRoutePathFromUrl() {
        /**
         * @param url example: /a/@id1_URL_ENCODED@id2_URL_ENCODED/c/d/@id3_URL_ENCODED
         * @return example: /a/@{}@{}/c/d/@{}
         */
        assertEquals("/a/@{}@{}/c/d/@{}", UrlUtil.genRoutePathFromUrl("/a/@id1LNa%2F.q%2F%5D%60pj3p@%40%40%40%2F%2F%2F%2F.../c/d/@%40%40%40%2F%2F%2F%2F..."));
    }

    @Test
    void genRoutePathAndPathVariableNamesFromUrlOnRequestMapping() {

        /**
         * @param urlOnRequestMapping example: /a/@{id1}@{id2}/c/d/@{id3}
         * @return example: /a/@{}@{}/c/d/@{} , [id1, id2, id3]
         */
        assertEquals("/a/@{}@{}/c/d/@{}", UrlUtil.genRoutePathAndPathVariableNamesFromUrlOnRequestMapping("/a/@{id1}@{id2}/c/d/@{id3}").getKey());
        assertArrayEquals(new String[]{"id1", "id2", "id3"}, UrlUtil.genRoutePathAndPathVariableNamesFromUrlOnRequestMapping("/a/@{id1}@{id2}/c/d/@{id3}").getValue());
    }

    @Test
    void genRoutePathFromUrlOnRequestMapping() {
        /**
         * @param urlOnRequestMapping example: /a/@{id1}@{id2}/c/d/@{id3}
         * @return example: /a/@{}@{}/c/d/@{}
         */
        assertEquals("/a/@{}@{}/c/d/@{}", UrlUtil.genRoutePathFromUrlOnRequestMapping("/a/@{id1}@{id2}/c/d/@{id3}"));
    }
}