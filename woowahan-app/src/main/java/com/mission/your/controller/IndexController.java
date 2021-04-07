package com.mission.your.controller;

import com.woowahan.framework.context.annotation.Controller;
import com.woowahan.framework.web.annotation.RequestMapping;
import com.woowahan.framework.web.annotation.RequestMethod;

/**
 * / path index.html을 불러온다.
 *
 * Created by Jaeseong on 2021/04/06
 * Git Hub : https://github.com/AnJaeSeongS2
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "index";
    }
}
