package com.ricardojosyferreira;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableRabbit
@SpringBootApplication
public class QimaApplication {

	public static void main(String[] args) {
		SpringApplication.run(QimaApplication.class, args);
	}

}
