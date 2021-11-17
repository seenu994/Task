package com.xyram.ticketingTool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.context.annotation.RequestScope;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;




@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
@ComponentScan(basePackages = { "com.xyram.ticketingTool" })
@EnableJpaRepositories
@EnableSwagger2
@EnableAutoConfiguration
@Configuration
public class TicketToolApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(TicketToolApplication.class, args);

	}

	@Bean
	@RequestScope
	public com.xyram.ticketingTool.request.CurrentUser requestedUserInfo() {
		return new com.xyram.ticketingTool.request.CurrentUser();
	}


	 @Bean
	    public Docket api() {
	        return new Docket(DocumentationType.SWAGGER_2)
	                .select()
	                .apis(RequestHandlerSelectors.any())
	                .paths(PathSelectors.any())
	                .build();
	
	
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