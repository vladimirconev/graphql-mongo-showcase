package com.example.graphqlshowcase.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.graphqlshowcase.adapter.in.BookRestController;
import com.example.graphqlshowcase.domain.BookDomainService;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class BookRestControllerConfig {
	
	private static final String IN_ADAPTER_BASE_PKG =
			"com.example.graphqlshowcase.adapter.in";
	
	@Bean
	public BookRestController bookRestController(final BookDomainService bookDomainService) {
		return new BookRestController(bookDomainService);
	}
	
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage(IN_ADAPTER_BASE_PKG)).build();
	}

}
