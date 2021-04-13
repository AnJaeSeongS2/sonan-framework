package org.sonan.framework.web.model.id;

import org.sonan.framework.web.annotation.model.IdAutoChangeableIfExists;

/**
 * @see IdAutoChangeableIfExists 이 붙어있는 field에 적용될 기능이다. 적용을 위해선 Model class는 이 interface의 구현체여야한다.
 * field의 id값이 X 이고 X에 해당하는 data가 이미 repo에 존재한다면, IdAutoSetter::changeIdAuto()가 동작한다.
 * changeIdAuto는 id값이 null이면 defaultValue를 채워줘야한다.
 *
 * 반환값은 변경된 id 이다.
 *
 * Created by Jaeseong on 2021/04/09
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public interface IdAutoChanger<IdType> {

    IdType changeIdAuto() throws FailedIdAutoChangeException;

    IdType defaultId();
}
