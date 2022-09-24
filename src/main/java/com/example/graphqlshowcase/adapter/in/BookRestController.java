package com.example.graphqlshowcase.adapter.in;

import com.example.graphqlshowcase.adapter.in.dto.request.CreateBookRequest;
import com.example.graphqlshowcase.adapter.in.dto.request.UpdateBookRequest;
import com.example.graphqlshowcase.adapter.in.dto.response.BookResponse;
import com.example.graphqlshowcase.adapter.in.dto.response.ErrorResponse;
import com.example.graphqlshowcase.domain.BookDomainService;
import com.example.graphqlshowcase.domain.valueobject.Genre;
import com.example.graphqlshowcase.domain.valueobject.ISBN;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequiredArgsConstructor
@Api(
    tags = {"Books"},
    value = "/books")
public class BookRestController {

  private final BookDomainService bookService;

  @ApiOperation(
      value = "Retrieve a book by ID",
      tags = {"Books"})
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "OK", response = BookResponse.class),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class),
        @ApiResponse(
            code = 503,
            message = "Service temporally unavailable",
            response = ErrorResponse.class)
      })
  @GetMapping(path = "/books/{bookId}")
  public ResponseEntity<BookResponse> retrieveBookById(
      final @PathVariable("bookId") String bookId) {
    var result = bookService.retrieveBookById(bookId);
    return new ResponseEntity<>(BookRestMapper.mapBookToBookResponse(result), HttpStatus.OK);
  }

  @ApiOperation(
      value = "Creates a book",
      tags = {"Books"})
  @ApiResponses(
      value = {
        @ApiResponse(code = 201, message = "Created", response = BookResponse.class),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
        @ApiResponse(
            code = 503,
            message = "Service temporally unavailable",
            response = ErrorResponse.class)
      })
  @PostMapping(
      path = "/books",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<BookResponse> createBook(
      final @RequestBody @Valid CreateBookRequest createBookRequest) {
    var authors = BookRestMapper.mapAuthorRequestsToAuthors(createBookRequest.getAuthors());
    var createdBook =
        bookService.createBook(
            new ISBN(createBookRequest.getIsbn()),
            Genre.valueOf(createBookRequest.getGenre()),
            createBookRequest.getTitle(),
            authors,
            BookRestMapper.mapPublisherRequestToPublisher(createBookRequest.getPublisher()));
    var output = BookRestMapper.mapBookToBookResponse(createdBook);
    return new ResponseEntity<>(output, HttpStatus.CREATED);
  }

  @ApiOperation(
      value = "Update a book info",
      tags = {"Books"})
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Updated", response = BookResponse.class),
        @ApiResponse(code = 422, message = "Unprocessable Entity"),
        @ApiResponse(code = 404, message = "Not found", response = ErrorResponse.class),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
        @ApiResponse(
            code = 503,
            message = "Service temporally unavailable",
            response = ErrorResponse.class)
      })
  @PutMapping(
      path = "/books",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<BookResponse> updateBook(
      final @RequestBody @Valid UpdateBookRequest updateBookRequest) {
    var book = BookRestMapper.mapUpdateBookRequestToBook(updateBookRequest);
    boolean wasAcknowledged = bookService.updateBook(book);
    if (wasAcknowledged) {
      var updatedBook = bookService.retrieveBookById(updateBookRequest.getId());
      return new ResponseEntity<>(BookRestMapper.mapBookToBookResponse(updatedBook), HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
  }
}
