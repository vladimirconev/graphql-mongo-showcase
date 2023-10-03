package com.example.graphqlshowcase.adapter.in;

import com.example.graphqlshowcase.adapter.in.dto.request.AddressRequest;
import com.example.graphqlshowcase.adapter.in.dto.request.AuthorRequest;
import com.example.graphqlshowcase.adapter.in.dto.request.PublisherRequest;
import com.example.graphqlshowcase.adapter.in.dto.request.UpdateBookRequest;
import com.example.graphqlshowcase.adapter.in.dto.response.AddressResponse;
import com.example.graphqlshowcase.adapter.in.dto.response.AuthorResponse;
import com.example.graphqlshowcase.adapter.in.dto.response.BookResponse;
import com.example.graphqlshowcase.adapter.in.dto.response.PublisherResponse;
import com.example.graphqlshowcase.domain.entity.Book;
import com.example.graphqlshowcase.domain.valueobject.Address;
import com.example.graphqlshowcase.domain.valueobject.Author;
import com.example.graphqlshowcase.domain.valueobject.Genre;
import com.example.graphqlshowcase.domain.valueobject.ISBN;
import com.example.graphqlshowcase.domain.valueobject.Publisher;
import java.util.List;
import java.util.stream.Collectors;

public class BookRestMapper {

  private BookRestMapper() {}

  public static BookResponse mapBookToBookResponse(final Book book) {
    return new BookResponse(
        book.id(),
        book.isbn().isbn(),
        book.title(),
        mapAuthorsToAuthorResponses(book.authors()),
        book.genre().name(),
        mapPublisherToPublisherResponse(book.publisher()));
  }

  private static PublisherResponse mapPublisherToPublisherResponse(final Publisher publisher) {
    return new PublisherResponse(
        publisher.name(), mapAddressToAddressResponse(publisher.address()));
  }

  private static AddressResponse mapAddressToAddressResponse(final Address address) {
    return new AddressResponse(
        address.state(),
        address.city(),
        address.houseNumber(),
        address.street(),
        address.zipCode());
  }

  public static Book mapUpdateBookRequestToBook(
      final UpdateBookRequest bookRequest, final String bookId) {
    var isbn = new ISBN(bookRequest.isbn());
    var genre = Genre.valueOf(bookRequest.genre());
    List<Author> authors = mapAuthorRequestsToAuthors(bookRequest.authors());
    return new Book(
        bookId,
        bookRequest.title(),
        isbn,
        authors,
        genre,
        mapPublisherRequestToPublisher(bookRequest.publisher()));
  }

  public static Publisher mapPublisherRequestToPublisher(final PublisherRequest publisherRequest) {
    return new Publisher(
        publisherRequest.name(), mapAddressRequestToAddress(publisherRequest.address()));
  }

  private static Address mapAddressRequestToAddress(final AddressRequest addressRequest) {
    return new Address(
        addressRequest.houseNumber(),
        addressRequest.state(),
        addressRequest.city(),
        addressRequest.zipCode(),
        addressRequest.street());
  }

  public static List<Author> mapAuthorRequestsToAuthors(final List<AuthorRequest> authorRequests) {
    return authorRequests.stream()
        .map(BookRestMapper::mapAuthorRequestToAuthor)
        .collect(Collectors.toList());
  }

  private static Author mapAuthorRequestToAuthor(final AuthorRequest authorRequest) {
    return new Author(authorRequest.firstName(), authorRequest.lastName(), authorRequest.email());
  }

  private static List<AuthorResponse> mapAuthorsToAuthorResponses(final List<Author> authors) {
    return authors.stream()
        .map(BookRestMapper::mapAuthorToAuthorResponse)
        .collect(Collectors.toList());
  }

  private static AuthorResponse mapAuthorToAuthorResponse(final Author author) {
    return new AuthorResponse(author.firstName(), author.lastName(), author.email());
  }
}
