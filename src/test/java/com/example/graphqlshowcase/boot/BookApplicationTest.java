package com.example.graphqlshowcase.boot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.graphqlshowcase.adapter.in.BookRestController;
import com.example.graphqlshowcase.adapter.in.GraphQLBookRestController;
import com.example.graphqlshowcase.adapter.in.dto.response.BookResponse;
import com.example.graphqlshowcase.domain.valueobject.Genre;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@Testcontainers(disabledWithoutDocker = true)
@AutoConfigureMockMvc
class BookApplicationTest {

  static final MongoDBContainer mongoDBContainer =
      new MongoDBContainer(DockerImageName.parse("mongo:latest")).withReuse(true);

  @DynamicPropertySource
  static void overrideProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.data.mongodb.host", mongoDBContainer::getHost);
    registry.add("spring.data.mongodb.port", mongoDBContainer::getFirstMappedPort);
  }

  @Autowired private BookRestController bookRestController;

  @Autowired private GraphQLBookRestController graphQLBookRestController;

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

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
  void createBook() throws Exception {
    var createBookRequest = TestObjects.createBookRequest();
    var mvcResult =
        mockMvc
            .perform(
                post("/books")
                    .content(objectMapper.writeValueAsString(createBookRequest))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andReturn();
    var response = mvcResult.getResponse();
    assertThat(response).isNotNull();
    var bookResponse = objectMapper.readValue(response.getContentAsString(), BookResponse.class);
    assertThat(bookResponse).isNotNull();

    mvcResult =
        mockMvc
            .perform(
                get("/books/%s".formatted(bookResponse.id()))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    var bookResponseOnGet =
        objectMapper.readValue(mvcResult.getResponse().getContentAsString(), BookResponse.class);
    assertThat(bookResponseOnGet).isNotNull();
    assertEquals(bookResponse.id(), bookResponseOnGet.id());
    assertEquals(bookResponse.title(), bookResponseOnGet.title());
    assertEquals(bookResponse.genre(), bookResponseOnGet.genre());
  }

  @Test
  void updateBook() throws Exception {
    var createBookRequest = TestObjects.createBookRequest();
    var mvcResult =
        mockMvc
            .perform(
                post("/books")
                    .content(objectMapper.writeValueAsString(createBookRequest))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andReturn();
    var response = mvcResult.getResponse();
    assertThat(response).isNotNull();
    var bookResponse = objectMapper.readValue(response.getContentAsString(), BookResponse.class);
    assertThat(bookResponse).isNotNull();

    var bookId = bookResponse.id();
    var bookUpdateRequest =
        TestObjects.updateBookRequestByTitleAndGenre("TEST_UPDATE", Genre.FANTASY.name());
    mvcResult =
        mockMvc
            .perform(
                put("/books/%s".formatted(bookId))
                    .content(objectMapper.writeValueAsString(bookUpdateRequest))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
    var bookResponseUponUpdate =
        objectMapper.readValue(mvcResult.getResponse().getContentAsString(), BookResponse.class);

    assertNotNull(bookResponseUponUpdate.title());
    assertEquals(bookUpdateRequest.title(), bookResponseUponUpdate.title());
    assertNotNull(bookResponseUponUpdate.genre());
    assertEquals(bookUpdateRequest.genre(), bookResponseUponUpdate.genre());
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
