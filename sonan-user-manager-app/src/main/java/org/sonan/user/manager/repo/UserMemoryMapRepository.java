package org.sonan.user.manager.repo;

import org.sonan.user.manager.model.User;
import org.sonan.framework.context.annotation.Repository;
import org.sonan.framework.repository.memory.GenericMapRepository;

/**
 * User model data를 memory에 저장하는 Repository다.
 *
 * Created by Jaeseong on 2021/04/06
 * Git Hub : https://github.com/AnJaeSeongS2
 */

@Repository
public class UserMemoryMapRepository extends GenericMapRepository<User> {
    public UserMemoryMapRepository() {
        super();
    }
}
