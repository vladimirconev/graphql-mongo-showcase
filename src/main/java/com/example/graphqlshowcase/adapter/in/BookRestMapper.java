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
    var bookResponse = new BookResponse();
    bookResponse.setId(book.id());
    bookResponse.setGenre(book.genre().name());
    bookResponse.setIsbn(book.isbn().isbn());
    bookResponse.setTitle(book.title());
    bookResponse.setAuthors(mapAuthorsToAuthorResponses(book.authors()));
    bookResponse.setPublisher(mapPublisherToPublisherResponse(book.publisher()));
    return bookResponse;
  }

  private static PublisherResponse mapPublisherToPublisherResponse(final Publisher publisher) {
    var publisherResponse = new PublisherResponse();
    publisherResponse.setName(publisher.name());
    publisherResponse.setAddress(mapAddressToAddressResponse(publisher.address()));
    return publisherResponse;
  }

  private static AddressResponse mapAddressToAddressResponse(final Address address) {
    var addressResponse = new AddressResponse();
    addressResponse.setCity(address.city());
    addressResponse.setHouseNumber(address.houseNumber());
    addressResponse.setState(address.state());
    addressResponse.setStreet(address.street());
    addressResponse.setZipCode(address.zipCode());
    return addressResponse;
  }

  public static Book mapUpdateBookRequestToBook(
      final UpdateBookRequest bookRequest, final String bookId) {
    var isbn = new ISBN(bookRequest.getIsbn());
    var genre = Genre.valueOf(bookRequest.getGenre());
    List<Author> authors = mapAuthorRequestsToAuthors(bookRequest.getAuthors());
    return new Book(
        bookId,
        bookRequest.getTitle(),
        isbn,
        authors,
        genre,
        mapPublisherRequestToPublisher(bookRequest.getPublisher()));
  }

  public static Publisher mapPublisherRequestToPublisher(final PublisherRequest publisherRequest) {
    return new Publisher(
        publisherRequest.getName(), mapAddressRequestToAddress(publisherRequest.getAddress()));
  }

  private static Address mapAddressRequestToAddress(final AddressRequest addressRequest) {
    return new Address(
        addressRequest.getHouseNumber(),
        addressRequest.getState(),
        addressRequest.getCity(),
        addressRequest.getZipCode(),
        addressRequest.getStreet());
  }

  public static List<Author> mapAuthorRequestsToAuthors(final List<AuthorRequest> authorRequests) {
    return authorRequests.stream()
        .map(BookRestMapper::mapAuthorRequestToAuthor)
        .collect(Collectors.toList());
  }

  private static Author mapAuthorRequestToAuthor(final AuthorRequest authorRequest) {
    return new Author(
        authorRequest.getFirstName(), authorRequest.getLastName(), authorRequest.getEmail());
  }

  private static List<AuthorResponse> mapAuthorsToAuthorResponses(final List<Author> authors) {
    return authors.stream()
        .map(BookRestMapper::mapAuthorToAuthorResponse)
        .collect(Collectors.toList());
  }

  private static AuthorResponse mapAuthorToAuthorResponse(final Author author) {
    var authorResponse = new AuthorResponse();
    authorResponse.setEmail(author.email());
    authorResponse.setFirstName(author.firstName());
    authorResponse.setLastName(author.lastName());
    return authorResponse;
  }
}
