package org.sonan.user.manager.controller;

import org.sonan.framework.context.annotation.Controller;
import org.sonan.framework.web.annotation.RequestMapping;
import org.sonan.framework.web.annotation.RequestMethod;

/**
 * / path index.html을 불러온다.
 *
 * Created by Jaeseong on 2021/04/06
 * Git Hub : https://github.com/AnJaeSeongS2
 */
@Controller
@RequestMapping("/")
public class HomeController {

    @RequestMapping(method = RequestMethod.GET)
    public String home() {
        return "index.html";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        // redirect to home.
        return "redirect:/";
    }
}
