package com.example.graphqlshowcase.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.graphqlshowcase.adapter.in.BookRestController;
import com.example.graphqlshowcase.domain.BookDomainService;

@Configuration
public class BookRestControllerConfig {

	@Bean
	public BookRestController bookRestController(final BookDomainService bookDomainService) {
		return new BookRestController(bookDomainService);
	}

}
