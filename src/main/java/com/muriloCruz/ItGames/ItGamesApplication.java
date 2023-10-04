package com.muriloCruz.ItGames;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.muriloCruz.ItGames.*","org.springdoc"})
public class ItGamesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItGamesApplication.class, args);
	}

}
