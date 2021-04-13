package org.sonan.user.manager;

import org.sonan.framework.container.SonanWebApplication;
import org.sonan.framework.container.annotation.ContainerBootApplication;
import org.sonan.framework.container.throwable.BootingFailException;

@ContainerBootApplication
public class UserManagerApplication {
    public static void main(String[] args) throws BootingFailException {
        SonanWebApplication.run(UserManagerApplication.class, args);
    }
}