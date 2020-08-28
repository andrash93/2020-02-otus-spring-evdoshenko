package ru.mango.telegram.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
   // docker build --tag=telegram-app:v8 --rm=true /Users/andrey/Dropbox/dev/mango-itg/telegram-bot/target/
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }
}