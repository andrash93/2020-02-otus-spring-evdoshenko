package ru.mango.vpbx.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("services")
public class ServiceLocationConfig {

    private String itgServerService;

    public String getItgServerService() {
        return itgServerService;
    }

    public void setItgServerService(String itgServerService) {
        this.itgServerService = itgServerService;
    }
}


