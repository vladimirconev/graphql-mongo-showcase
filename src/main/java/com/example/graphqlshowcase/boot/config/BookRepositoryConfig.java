package com.example.graphqlshowcase.boot.config;

import com.example.graphqlshowcase.adapter.out.MongoBookRepository;
import com.example.graphqlshowcase.domain.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;

@Configuration
public class BookRepositoryConfig {

  @Autowired private MongoOperations mongoOperations;

  @Bean
  public BookRepository bookRepository() {
    return new MongoBookRepository(mongoOperations);
  }
}
