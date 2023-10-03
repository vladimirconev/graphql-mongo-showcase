package com.example.graphqlshowcase.adapter.in;

import com.example.graphqlshowcase.adapter.in.dto.request.CreateBookRequest;
import com.example.graphqlshowcase.adapter.in.dto.request.UpdateBookRequest;
import com.example.graphqlshowcase.adapter.in.dto.response.BookResponse;
import com.example.graphqlshowcase.adapter.in.dto.response.ErrorResponse;
import com.example.graphqlshowcase.domain.BookDomainService;
import com.example.graphqlshowcase.domain.valueobject.Genre;
import com.example.graphqlshowcase.domain.valueobject.ISBN;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "Books")
public class BookRestController {

  private final BookDomainService bookService;

  public BookRestController(final BookDomainService bookService) {
    this.bookService = bookService;
  }

  @Operation(
      summary = "Retrieve a book by ID",
      tags = {"Books"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "OK",
            content = @Content(schema = @Schema(implementation = BookResponse.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "503",
            description = "Service temporally unavailable",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
      })
  @GetMapping(path = "/books/{bookId}")
  public ResponseEntity<BookResponse> retrieveBookById(
      final @PathVariable("bookId") String bookId) {
    var result = bookService.retrieveBookById(bookId);
    return new ResponseEntity<>(BookRestMapper.mapBookToBookResponse(result), HttpStatus.OK);
  }

  @Operation(
      summary = "Creates a book",
      tags = {"Books"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Created",
            content = @Content(schema = @Schema(implementation = BookResponse.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "503",
            description = "Service temporally unavailable",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
      })
  @PostMapping(
      path = "/books",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<BookResponse> createBook(
      final @RequestBody @Valid CreateBookRequest createBookRequest) {
    var authors = BookRestMapper.mapAuthorRequestsToAuthors(createBookRequest.authors());
    var createdBook =
        bookService.createBook(
            new ISBN(createBookRequest.isbn()),
            Genre.valueOf(createBookRequest.genre()),
            createBookRequest.title(),
            authors,
            BookRestMapper.mapPublisherRequestToPublisher(createBookRequest.publisher()));
    var output = BookRestMapper.mapBookToBookResponse(createdBook);
    return new ResponseEntity<>(output, HttpStatus.CREATED);
  }

  @Operation(
      summary = "Update a book info",
      tags = {"Books"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Updated",
            content = @Content(schema = @Schema(implementation = BookResponse.class))),
        @ApiResponse(responseCode = "422", description = "Unprocessable Entity"),
        @ApiResponse(
            responseCode = "404",
            description = "Not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "503",
            description = "Service temporally unavailable",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
      })
  @PutMapping(
      path = "/books/{bookId}",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<BookResponse> updateBook(
      final @RequestBody @Valid UpdateBookRequest updateBookRequest,
      final @PathVariable("bookId") String bookId) {
    var book = BookRestMapper.mapUpdateBookRequestToBook(updateBookRequest, bookId);
    boolean wasAcknowledged = bookService.updateBook(book);
    if (wasAcknowledged) {
      var updatedBook = bookService.retrieveBookById(bookId);
      return new ResponseEntity<>(BookRestMapper.mapBookToBookResponse(updatedBook), HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
  }
}
