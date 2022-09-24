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
    bookResponse.setId(book.getId());
    bookResponse.setGenre(book.getGenre().name());
    bookResponse.setIsbn(book.getIsbn().getIsbn());
    bookResponse.setTitle(book.getTitle());
    bookResponse.setAuthors(mapAuthorsToAuthorResponses(book.getAuthors()));
    bookResponse.setPublisher(mapPublisherToPublisherResponse(book.getPublisher()));
    return bookResponse;
  }

  private static PublisherResponse mapPublisherToPublisherResponse(final Publisher publisher) {
    var publisherResponse = new PublisherResponse();
    publisherResponse.setName(publisher.getName());
    publisherResponse.setAddress(mapAddressToAddressResponse(publisher.getAddress()));
    return publisherResponse;
  }

  private static AddressResponse mapAddressToAddressResponse(final Address address) {
    var addressResponse = new AddressResponse();
    addressResponse.setCity(address.getCity());
    addressResponse.setHouseNumber(address.getHouseNumber());
    addressResponse.setState(address.getState());
    addressResponse.setStreet(address.getStreet());
    addressResponse.setZipCode(address.getZipCode());
    return addressResponse;
  }

  public static Book mapUpdateBookRequestToBook(final UpdateBookRequest bookRequest) {
    var isbn = new ISBN(bookRequest.getIsbn());
    var genre = Genre.valueOf(bookRequest.getGenre());
    List<Author> authors = mapAuthorRequestsToAuthors(bookRequest.getAuthors());
    return new Book(
        bookRequest.getId(),
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
    authorResponse.setEmail(author.getEmail());
    authorResponse.setFirstName(author.getFirstName());
    authorResponse.setLastName(author.getLastName());
    return authorResponse;
  }
}
