package com.cjs.example.util;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author ChengJianSheng
 * @date 2019-02-17
 * 凡注册到Spring容器内的bean，实现了EnvironmentAware接口重写setEnvironment方法后，
 * 在工程启动时可以获得application.properties的配置文件配置的属性值。
 */
@Component
public class EnvironmentUtils implements EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public String getActiveProfile() {
        if (environment.getActiveProfiles().length > 0) {
            return environment.getActiveProfiles()[0];
        }
        return environment.getDefaultProfiles()[0];
    }
}
