package com.sajdakk.flipbook;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FlipbookApplication {
    public static void main(String[] args) {
        // Załaduj zmienne środowiskowe z pliku .env
        Dotenv dotenv = Dotenv.load();

        // Ustaw zmienne środowiskowe jako właściwości systemowe
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

        SpringApplication.run(FlipbookApplication.class, args);
    }
}
