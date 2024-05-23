package com.springboot.BuidingRealTimeAPI;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Spring Boot Blog App Rest API",
				description = "Spring Boot Blog App Rest API Documentation",
				version = "v1.0",
				contact = @Contact(
						name = "Rushikesh Yadav",
						email = "rushikeshyadav827@gmail.com",
						url = "http://www.rushiyadav.git"
				),
				license = @License(
						name = "Apache 2.0",
						url = "http://www.rushiyadav.git/license"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring Boot Blog App Documentation",
				url = "https://github.com/rushikesh/springboot-blog-rest-api"
		)
)
public class BuidingRealTimeApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BuidingRealTimeApiApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
}
