package ru.mango.telegram.bot.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class TelegramHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        return Health.up().withDetail("message", "version7").build();
    }
}
