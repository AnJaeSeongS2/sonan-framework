package org.sonan.user.manager.controller;

import org.sonan.user.manager.model.User;
import org.sonan.user.manager.repo.UserMemoryMapRepository;
import org.sonan.framework.context.annotation.Autowired;
import org.sonan.framework.context.annotation.BeanVariable;
import org.sonan.framework.context.annotation.Controller;
import org.sonan.framework.json.throwable.FailedConvertJsonException;
import org.sonan.framework.repository.RepositoryAccessibleAll;
import org.sonan.framework.throwable.FailedDeleteException;
import org.sonan.framework.throwable.FailedGetException;
import org.sonan.framework.throwable.FailedPostException;
import org.sonan.framework.throwable.FailedPutException;
import org.sonan.framework.web.annotation.*;

import java.util.List;

/**
 * User Controller다.
 *
 * 요건상 View에 model data를 전달 하지 않고, restapi로서 json data만 반환한다.
 *
 * Created by Jaeseong on 2021/04/06
 * Git Hub : https://github.com/AnJaeSeongS2
 */
@Controller
@RequestMapping("/users")
public class UserController {

    // TODO: app개발자가 repo vendor를 직접 지정해서 코딩하는게 아니라, 사용자입장에서는 vendor무관히 코딩가능하게 개선할 것.
    private final RepositoryAccessibleAll<User> userRepo;

    @Autowired
    public UserController(@BeanVariable UserMemoryMapRepository userRepo) {
        this.userRepo = userRepo;
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<User> getAll() throws FailedGetException, FailedConvertJsonException {
        return userRepo.getAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void post(@RequestBody User user) throws FailedPostException {
        userRepo.post(user);
    }

    @RequestMapping(value = "/@{id}", method = RequestMethod.PUT)
    public void put(@PathVariable("id") Integer id, @RequestBody User user) throws FailedPutException {
        user.setId(id);
        userRepo.put(user);
    }

    @RequestMapping(value = "/@{id}", method = RequestMethod.DELETE)
    public @ResponseBody
    User delete(@PathVariable("id") Integer id) throws FailedDeleteException, FailedConvertJsonException {
        return userRepo.delete(id);
    }

    @RequestMapping(value = "/@{id}", method = RequestMethod.GET)
    public @ResponseBody
    User get(@PathVariable("id") Integer id) throws FailedGetException, FailedConvertJsonException {
        return userRepo.get(id);
    }
}
