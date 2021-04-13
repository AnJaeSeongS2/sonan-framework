package org.sonan.logback.support;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * logback에서 사용할 적절한 Marker를 미리 정의해둔다.
 *
 * Created by Jaeseong on 2021/04/02
 * Git Hub : https://github.com/AnJaeSeongS2
 */
public enum Markers {
    MESSAGE(MarkerFactory.getMarker("MESSAGE")),
    LIFE_CYCLE(MarkerFactory.getMarker("LIFE_CYCLE")),
    DEV(MarkerFactory.getMarker("DEV")),
    PROD(MarkerFactory.getMarker("PROD"));

    private Marker marker;

    Markers(Marker marker) {
        this.marker = marker;
    }

    public Marker get() {
        return marker;
    }
}
