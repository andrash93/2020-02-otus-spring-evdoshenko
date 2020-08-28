package ru.mango.vpbx.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.MessageChannel;

@Configuration
@EnableIntegration
@ComponentScan("ru.mango.vpbx.events")
@IntegrationComponentScan("ru.mango.vpbx.events")
public class ChanelConfig {

    @Bean
    public MessageChannel callEventChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel summaryRouterChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel callEventRouterOutputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel callEventFilterOutputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel callRouterIvrAppearedChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel callRouterIvrDisconnectedChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel callRouterAbonentAppearedChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel callRouterAbonentConnectedChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel callRouterAbonentDisconnectedChannel() {
        return new DirectChannel();
    }

}
