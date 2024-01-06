package com.muriloCruz.ItGames;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
public class ItGamesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItGamesApplication.class, args);
	}
	
//	@Bean
//	public Hibernate5JakartaModule jsonHibernate5Module() {
//		return new Hibernate5JakartaModule();
//	}
	
	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			
			System.out.println("The system started");
		};
	}

}
