package ru.mango.server.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class VpbxHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        return Health.up().withDetail("message", "itg-version6").build();
    }
}
