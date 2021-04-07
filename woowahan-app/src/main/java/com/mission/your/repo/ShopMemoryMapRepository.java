package com.mission.your.repo;

import com.mission.your.model.Shop;
import com.woowahan.framework.context.annotation.Repository;
import com.woowahan.framework.repository.memory.GenericMapRepository;

/**
 * Shop model data를 memory에 저장하는 Repository다.
 *
 * Created by Jaeseong on 2021/04/06
 * Git Hub : https://github.com/AnJaeSeongS2
 */

@Repository
public class ShopMemoryMapRepository extends GenericMapRepository<Shop> {
    public ShopMemoryMapRepository() {
        super();
    }
}
