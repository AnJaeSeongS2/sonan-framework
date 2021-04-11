package com.mission.your;

import com.woowahan.framework.container.WoowahanApplication;
import com.woowahan.framework.container.annotation.ContainerBootApplication;
import com.woowahan.framework.container.throwable.BootingFailException;

@ContainerBootApplication
public class MissionApplication {
    public static void main(String[] args) throws BootingFailException {
        WoowahanApplication.run(MissionApplication.class, args);
    }
}