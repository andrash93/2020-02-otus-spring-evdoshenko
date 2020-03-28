package ru.otus.testing.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties("localization-settings")
public class LocalizationProperties {

    private Map<String, String> local;
    private Map<String, String> localPath;

    public Map<String, String> getLocal() {
        return local;
    }

    public void setLocal(Map<String, String> local) {
        this.local = local;
    }

    public Map<String, String> getLocalPath() {
        return localPath;
    }

    public void setLocalPath(Map<String, String> localPath) {
        this.localPath = localPath;
    }
}
