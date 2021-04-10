package com.mission.your.controller;

import com.mission.your.model.Shop;
import com.mission.your.repo.ShopMemoryMapRepository;
import com.woowahan.framework.context.annotation.Autowired;
import com.woowahan.framework.context.annotation.BeanVariable;
import com.woowahan.framework.context.annotation.Controller;
import com.woowahan.framework.json.throwable.FailedConvertJsonException;
import com.woowahan.framework.repository.RepositoryAccessibleAll;
import com.woowahan.framework.throwable.FailedDeleteException;
import com.woowahan.framework.throwable.FailedGetException;
import com.woowahan.framework.throwable.FailedPostException;
import com.woowahan.framework.throwable.FailedPutException;
import com.woowahan.framework.web.annotation.*;

import java.util.List;

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
    private final RepositoryAccessibleAll<Shop> shopRepo;

    @Autowired
    public ShopController(@BeanVariable ShopMemoryMapRepository shopRepo) {
        this.shopRepo = shopRepo;
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Shop> getAll() throws FailedGetException, FailedConvertJsonException {
        return shopRepo.getAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void post(@RequestBody Shop shop) throws FailedPostException {
        shopRepo.post(shop);
    }

    @RequestMapping(value = "/@{id}", method = RequestMethod.PUT)
    public void put(@PathVariable("id") Integer id, @RequestBody Shop shop) throws FailedPutException {
        shop.setId(id);
        shopRepo.put(shop);
    }

    @RequestMapping(value = "/@{id}", method = RequestMethod.DELETE)
    public @ResponseBody Shop delete(@PathVariable("id") Integer id) throws FailedDeleteException, FailedConvertJsonException {
        return shopRepo.delete(id);
    }

    @RequestMapping(value = "/@{id}", method = RequestMethod.GET)
    public @ResponseBody Shop get(@PathVariable("id") Integer id) throws FailedGetException, FailedConvertJsonException {
        return shopRepo.get(id);
    }
}
