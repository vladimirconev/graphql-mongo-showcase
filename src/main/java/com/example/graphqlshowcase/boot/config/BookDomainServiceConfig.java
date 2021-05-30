package com.example.graphqlshowcase.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.graphqlshowcase.domain.BookDomainService;
import com.example.graphqlshowcase.domain.BookRepository;
import com.example.graphqlshowcase.domain.BookService;

@Configuration
public class BookDomainServiceConfig {
	
	@Bean
	public BookDomainService bookDomainService(final BookRepository bookRepository) {
		return new BookService(bookRepository);
	}

}
