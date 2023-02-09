package com.modyo.wrapperapi;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.modyo.wrapperapi.controller"))
                .build().apiInfo(apiInfo());
	}
	
	private ApiInfo apiInfo() {
        return new ApiInfo(
          "API REST WRAPPER POKEMONS", 
          "API for Challenge", 
          "API TOS", 
          "Terms of service", 
          new Contact("cerjio", "", "@cerjio "), 
          "License of API", "API license URL", Collections.emptyList());
	}
	

}
