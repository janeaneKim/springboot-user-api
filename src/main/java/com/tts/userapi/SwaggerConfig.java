package com.tts.userapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {  
	private ApiInfo buildApiInfo() {
		return new ApiInfoBuilder()
				.title("User API")
				.description("REST API for interacting with user items")
				.version("1.0.0")
				.contact(new Contact("Janeane Kim", "jkim.com", "jmkfakeemail@gmail.com"))
				.license("Apache License Version 2.0")
				.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
				.build();
	}
	
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
        .select() 
        .apis(RequestHandlerSelectors.basePackage("com.tts.userapi"))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(buildApiInfo());
    }
    
    @Bean
    UiConfiguration buildConfig() {
    	return UiConfigurationBuilder.builder()
    			.docExpansion(DocExpansion.FULL)
    			.build();
    }
}
