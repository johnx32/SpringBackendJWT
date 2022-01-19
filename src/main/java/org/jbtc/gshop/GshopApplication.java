package org.jbtc.gshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class GshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(GshopApplication.class, args);
		System.out.println("iniciado");
	}


	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("*");
				//registry.addMapping("/**")
				//		.allowedOrigins("*")
				//		.allowedHeaders("*")
				//		.allowedMethods("GET","POST","PUT","DELETE")
				//		.allowCredentials(true);
			}
		};
	}

}
