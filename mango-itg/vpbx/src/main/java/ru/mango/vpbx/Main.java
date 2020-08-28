package ru.mango.vpbx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    // docker build --tag=vpbx-app:v10 --rm=true /Users/andrey/Dropbox/dev/mango-itg/vpbx/target
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }
}