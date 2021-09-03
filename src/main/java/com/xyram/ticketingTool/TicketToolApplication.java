package com.xyram.ticketingTool;

import org.springframework.boot.SpringApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
//@ComponentScan(basePackages = { "com.xyram.ticketingTool" })
@EnableJpaRepositories
@EnableAutoConfiguration
public class TicketToolApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(TicketToolApplication.class, args);
	}

}
	
/*
 * @Bean public CorsFilter corsFilter() {
 * 
 * UrlBasedCorsConfigurationSource source = new
 * UrlBasedCorsConfigurationSource(); CorsConfiguration config = new
 * CorsConfiguration();
 * 
 * // you USUALLY want this // config.setAllowCredentials(true);
 * config.addAllowedOrigin("*"); config.addAllowedHeader("*");
 * config.addAllowedMethod("OPTIONS"); config.addAllowedMethod("HEAD");
 * config.addAllowedMethod("GET"); config.addAllowedMethod("PUT");
 * config.addAllowedMethod("POST"); config.addAllowedMethod("DELETE");
 * config.addAllowedMethod("PATCH"); source.registerCorsConfiguration("/**",
 * config); return new CorsFilter(source);
 * 
 * }
 * 
 * }
 */