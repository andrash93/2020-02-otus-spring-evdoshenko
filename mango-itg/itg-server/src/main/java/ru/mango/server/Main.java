package ru.mango.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
   // docker build --tag=itg-app:v6 --rm=true /Users/andrey/Dropbox/dev/mango-itg/itg-server/target/
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }
}