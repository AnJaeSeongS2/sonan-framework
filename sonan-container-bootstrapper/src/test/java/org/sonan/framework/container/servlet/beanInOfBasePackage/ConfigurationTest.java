package org.sonan.framework.container.servlet.beanInOfBasePackage;

import org.sonan.framework.context.annotation.Bean;
import org.sonan.framework.context.annotation.Configuration;

/**
 * Created by Jaeseong on 2021/04/04
 * Git Hub : https://github.com/AnJaeSeongS2
 */
@Configuration
public class ConfigurationTest {

    @Bean
    public String genBean() {
        return "genBean";
    }
}
