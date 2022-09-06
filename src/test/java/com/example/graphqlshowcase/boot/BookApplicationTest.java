package com.example.graphqlshowcase.boot;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.graphqlshowcase.adapter.in.BookRestController;
import com.example.graphqlshowcase.adapter.in.GraphQLBookRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookApplicationTest {

  @Autowired private BookRestController bookRestController;

  @Autowired private GraphQLBookRestController graphQLBookRestController;

  @Test
  void contextLoads() {
    assertNotNull(bookRestController);
    assertNotNull(graphQLBookRestController);
  }
}
