package ru.mango.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.MessageChannel;

@Configuration
@EnableIntegration
@ComponentScan("ru.mango.server.events")
@IntegrationComponentScan("ru.mango.server.events")
public class ChanelConfig {

    @Bean
    public MessageChannel telegramMessageChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel privateMessageRouterChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel groupMessageRouterChannel() {
        return new DirectChannel();
    }

}
