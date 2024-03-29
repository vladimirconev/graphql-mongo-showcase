package com.example.graphqlshowcase.boot.config;

import com.example.graphqlshowcase.boot.BookApplicationStartupListener;
import com.example.graphqlshowcase.domain.BookRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookAppStartupListenerConfig {

  @Value("${demo.initial.data.load}")
  private Boolean initialDataLoad;

  @Bean
  public BookApplicationStartupListener bookApplicationStartupListener(
      final BookRepository bookRepository) {
    return new BookApplicationStartupListener(initialDataLoad, bookRepository);
  }
}
