package com.raukhvarger.ms.webfs.spring;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "webfs")
public class AppConfig {

    private Boolean disableAuthorization = false;

    private String startDir;
}
