package com.example.graphqlshowcase.boot.config;

import com.example.graphqlshowcase.adapter.in.BookRestController;
import com.example.graphqlshowcase.domain.BookDomainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookRestControllerConfig {

  @Bean
  public BookRestController bookRestController(final BookDomainService bookDomainService) {
    return new BookRestController(bookDomainService);
  }


}
