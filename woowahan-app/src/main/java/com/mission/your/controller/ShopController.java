package com.mission.your.controller;

import com.mission.your.repo.ShopMemoryMapRepository;
import com.woowahan.framework.context.annotation.Autowired;
import com.woowahan.framework.context.annotation.BeanVariable;
import com.woowahan.framework.context.annotation.Controller;
import com.woowahan.framework.json.JacksonUtil;
import com.woowahan.framework.json.throwable.FailedConvertJsonException;
import com.woowahan.framework.throwable.FailedDeleteException;
import com.woowahan.framework.throwable.FailedGetException;
import com.woowahan.framework.throwable.FailedPostException;
import com.woowahan.framework.throwable.FailedPutException;
import com.woowahan.framework.web.annotation.PathVariable;
import com.woowahan.framework.web.annotation.RequestMapping;
import com.woowahan.framework.web.annotation.RequestMethod;

/**
 * Shop Controller다.
 *
 * 요건상 View에 model data를 전달 하지 않고, restapi로서 json data만 반환한다.
 *
 * Created by Jaeseong on 2021/04/06
 * Git Hub : https://github.com/AnJaeSeongS2
 */
@Controller
@RequestMapping("/shops")
public class ShopController {

    // TODO: app개발자가 repo vendor를 직접 지정해서 코딩하는게 아니라, 사용자입장에서는 vendor무관히 코딩가능하게 개선할 것.
    private final ShopMemoryMapRepository shopRepo;

    @Autowired
    protected ShopController(@BeanVariable ShopMemoryMapRepository shopRepo) {
        this.shopRepo = shopRepo;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getAll() throws FailedGetException, FailedConvertJsonException {
        return JacksonUtil.toJson(shopRepo.getAll());
    }

    @RequestMapping(method = RequestMethod.POST)
    public void post() throws FailedPostException {
        // TODO
        throw new FailedPostException("no method code");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void put(@PathVariable("id") Integer id) throws FailedPutException {
        // TODO
        throw new FailedPutException("no method code");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Integer id) throws FailedDeleteException, FailedConvertJsonException {
        return JacksonUtil.toJson(shopRepo.delete(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String get(@PathVariable("id") Integer id) throws FailedGetException, FailedConvertJsonException {
        return JacksonUtil.toJson(shopRepo.get(id));
    }
}
