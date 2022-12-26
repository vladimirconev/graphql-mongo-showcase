package com.example.graphqlshowcase.boot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.example.graphqlshowcase.adapter.in.BookRestController;
import com.example.graphqlshowcase.adapter.in.GraphQLBookRestController;
import com.example.graphqlshowcase.adapter.in.dto.response.BookResponse;
import com.example.graphqlshowcase.domain.valueobject.Genre;
import java.util.HashMap;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@Testcontainers(disabledWithoutDocker = true)
class BookApplicationTest {

  @Container
  private static final MongoDBContainer mongoDBContainer =
      new MongoDBContainer(DockerImageName.parse("mongo:latest"));

  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.data.mongodb.host", mongoDBContainer::getHost);
    registry.add("spring.data.mongodb.port", mongoDBContainer::getFirstMappedPort);
  }

  @Autowired private BookRestController bookRestController;

  @Autowired private GraphQLBookRestController graphQLBookRestController;

  @BeforeAll
  static void setUp() {
    mongoDBContainer.start();
  }

  @AfterAll
  static void tearDown() {
    mongoDBContainer.close();
    assertThat(mongoDBContainer.isRunning()).isFalse();
  }

  @BeforeEach
  void testIsContainerRunning() {
    assertThat(mongoDBContainer.isRunning()).isTrue();
  }

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
    assertThat(responseEntity.getBody()).isInstanceOf(BookResponse.class);
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

    assert bookResponseUponUpdate != null;
    assertNotNull(bookResponseUponUpdate.getTitle());
    assertEquals(bookUpdateRequest.getTitle(), bookResponseUponUpdate.getTitle());
    assertNotNull(bookResponseUponUpdate.getGenre());
    assertEquals(bookUpdateRequest.getGenre(), bookResponseUponUpdate.getGenre());
  }

  @Test
  void retrieveBooksViaPagination() {
    var payload = new HashMap<String, Object>();
    payload.putIfAbsent(
        "query",
        """
                    query GetAllBooks (
                        $size:Int!,\s
                        $offset:Int!) {
                        books(
                            size: $size,
                            offset:$offset) {
                                total
                                content{
                                    isbn
                                    title
                                    genre
                                    publisher {
                                        name
                                        address {
                                            city
                                            state
                                        }
                                    }
                                }
                            }
                    }""");
    var paginationVariables = new HashMap<String, Object>();
    paginationVariables.putIfAbsent("size", 2);
    paginationVariables.putIfAbsent("offset", 0);

    payload.putIfAbsent("variables", paginationVariables);

    ResponseEntity<?> responseEntity = graphQLBookRestController.retrieveBooks(payload);
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    var output = responseEntity.getBody();
    assertNotNull(output);
    assertThat(output).hasFieldOrProperty("books");
  }
}
