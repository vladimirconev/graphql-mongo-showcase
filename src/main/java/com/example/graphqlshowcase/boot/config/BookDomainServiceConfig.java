package com.example.graphqlshowcase.boot.config;

import com.example.graphqlshowcase.domain.BookDomainService;
import com.example.graphqlshowcase.domain.BookRepository;
import com.example.graphqlshowcase.domain.BookService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookDomainServiceConfig {

  @Bean
  public BookDomainService bookDomainService(final BookRepository bookRepository) {
    return new BookService(bookRepository);
  }
}
