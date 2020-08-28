package ru.mango.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("services")
public class ServiceLocationConfig {

    private String telegramBotService;
    private String vpbxService;

    public String getTelegramBotService() {
        return telegramBotService;
    }

    public void setTelegramBotService(String telegramBotService) {
        this.telegramBotService = telegramBotService;
    }

    public String getVpbxService() {
        return vpbxService;
    }

    public void setVpbxService(String vpbxService) {
        this.vpbxService = vpbxService;
    }
}



