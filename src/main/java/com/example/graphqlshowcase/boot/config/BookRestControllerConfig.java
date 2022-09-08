package com.example.graphqlshowcase.boot.config;

import com.example.graphqlshowcase.adapter.in.BookRestController;
import com.example.graphqlshowcase.domain.BookDomainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class BookRestControllerConfig {

  private static final String IN_ADAPTER_BASE_PKG = "com.example.graphqlshowcase.adapter.in";

  @Bean
  public BookRestController bookRestController(final BookDomainService bookDomainService) {
    return new BookRestController(bookDomainService);
  }

  @Bean
  public Docket productApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage(IN_ADAPTER_BASE_PKG))
        .build();
  }
}
