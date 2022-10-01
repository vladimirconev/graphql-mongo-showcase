package com.example.graphqlshowcase.boot;

import static org.junit.jupiter.api.Assertions.*;

import com.example.graphqlshowcase.adapter.in.BookRestController;
import com.example.graphqlshowcase.adapter.in.GraphQLBookRestController;
import com.example.graphqlshowcase.adapter.in.dto.response.BookResponse;
import com.example.graphqlshowcase.domain.valueobject.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest
class BookApplicationTest {

  @Autowired private BookRestController bookRestController;

  @Autowired private GraphQLBookRestController graphQLBookRestController;

  @Test
  void contextLoads() {
    assertNotNull(bookRestController);
    assertNotNull(graphQLBookRestController);
  }

  @Test
  void createBookAndRetrieveById() {
    var createBookRequest = TestObjects.createBookRequest();
    var responseEntity = bookRestController.createBook(createBookRequest);

    assertNotNull(responseEntity);
    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    assertNotNull(responseEntity.getBody());
    assertTrue(responseEntity.getBody() instanceof BookResponse);
    var bookResponse = responseEntity.getBody();
    assertNotNull(bookResponse.getId());
    assertEquals(bookResponse.getIsbn(), createBookRequest.getIsbn());
    assertEquals(bookResponse.getGenre(), createBookRequest.getGenre());

    var bookByIdResponseEntity = bookRestController.retrieveBookById(bookResponse.getId());
    assertEquals(HttpStatus.OK, bookByIdResponseEntity.getStatusCode());
    assertEquals(responseEntity.getBody(), bookByIdResponseEntity.getBody());
  }

  @Test
  void updateBook() {
    var createBookRequest = TestObjects.createBookRequest();
    var responseEntity = bookRestController.createBook(createBookRequest);

    assertNotNull(responseEntity);
    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    assertNotNull(responseEntity.getBody());

    var bookResponse = responseEntity.getBody();
    var bookId = bookResponse.getId();
    var bookUpdateRequest =
        TestObjects.updateBookRequestByTitleAndGenre("TEST_UPDATE", Genre.FANTASY.name());

    var bookResponseResponseEntity = bookRestController.updateBook(bookUpdateRequest, bookId);
    assertEquals(HttpStatus.OK, bookResponseResponseEntity.getStatusCode());
    var bookResponseUponUpdate = bookResponseResponseEntity.getBody();

    assertEquals(bookUpdateRequest.getTitle(), bookResponseUponUpdate.getTitle());
    assertEquals(bookUpdateRequest.getGenre(), bookResponseUponUpdate.getGenre());
  }
}
