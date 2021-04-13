package org.sonan.framework.web.model.id;

import java.lang.reflect.Field;

/**
 * Id값이 곂친 경우, ID값이 Integer이므로 자동으로 증가시켜준다.
 *
 * Created by Jaeseong on 2021/04/09
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public class DefaultIntegerIdAutoIncrementModel implements IdAutoChanger<Integer> {

    @Override
    public Integer changeIdAuto() throws FailedIdAutoChangeException {
        Integer resultId;
        try {
            Field idField = IdExtractor.getIdField(this);
            synchronized (idField) {
                boolean accessible = idField.isAccessible();
                try {
                    idField.setAccessible(true);

                    // autoIncrement
                    resultId = (Integer) idField.get(this);
                    resultId = resultId == null ? defaultId() : resultId + 1;
                    idField.set(this, resultId);
                } finally {
                    idField.setAccessible(accessible);
                }
            }
        } catch (Exception e) {
            throw new FailedIdAutoChangeException(String.format("DefaultIntegerIdAutoChanger is not working. target Model : %s", this, e));
        }
        return resultId;
    }

    @Override
    public Integer defaultId() {
        return 1;
    }


}
