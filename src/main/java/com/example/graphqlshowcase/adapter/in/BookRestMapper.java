package com.example.graphqlshowcase.adapter.in;

import com.example.graphqlshowcase.adapter.in.dto.request.AddressRequestDto;
import com.example.graphqlshowcase.adapter.in.dto.request.AuthorRequestDto;
import com.example.graphqlshowcase.adapter.in.dto.request.PublisherRequestDto;
import com.example.graphqlshowcase.adapter.in.dto.request.UpdateBookRequestDto;
import com.example.graphqlshowcase.adapter.in.dto.response.AddressResponseDto;
import com.example.graphqlshowcase.adapter.in.dto.response.AuthorResponseDto;
import com.example.graphqlshowcase.adapter.in.dto.response.BookResponseDto;
import com.example.graphqlshowcase.adapter.in.dto.response.PublisherResponseDto;
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

  public static BookResponseDto mapBookToBookResponseDto(final Book book) {
    var bookResponseDto = new BookResponseDto();
    bookResponseDto.setId(book.getId());
    bookResponseDto.setGenre(book.getGenre().name());
    bookResponseDto.setIsbn(book.getIsbn().getIsbn());
    bookResponseDto.setTitle(book.getTitle());
    bookResponseDto.setAuthors(mapAuthorsToAuthorResponseDtos(book.getAuthors()));
    bookResponseDto.setPublisher(mapPublisherToPublisherResponseDto(book.getPublisher()));
    return bookResponseDto;
  }

  private static PublisherResponseDto mapPublisherToPublisherResponseDto(
      final Publisher publisher) {
    var publisherResponseDto = new PublisherResponseDto();
    publisherResponseDto.setName(publisher.getName());
    publisherResponseDto.setAddress(mapAddressToAddressResponseDto(publisher.getAddress()));
    return publisherResponseDto;
  }

  private static AddressResponseDto mapAddressToAddressResponseDto(final Address address) {
    var addressResponseDto = new AddressResponseDto();
    addressResponseDto.setCity(address.getCity());
    addressResponseDto.setHouseNumber(address.getHouseNumber());
    addressResponseDto.setState(address.getState());
    addressResponseDto.setStreet(address.getStreet());
    addressResponseDto.setZipCode(address.getZipCode());
    return addressResponseDto;
  }

  public static Book mapUpdateBookRequestDtoToBook(final UpdateBookRequestDto bookRequestDto) {
    var isbn = new ISBN(bookRequestDto.getIsbn());
    var genre = Genre.valueOf(bookRequestDto.getGenre());
    List<Author> authors = mapAuthorRequestDtosToAuthors(bookRequestDto.getAuthors());
    return new Book(
        bookRequestDto.getId(),
        bookRequestDto.getTitle(),
        isbn,
        authors,
        genre,
        mapPublisherRequestDtoToPublisher(bookRequestDto.getPublisher()));
  }

  public static Publisher mapPublisherRequestDtoToPublisher(
      final PublisherRequestDto publisherRequestDto) {
    return new Publisher(
        publisherRequestDto.getName(),
        mapAddressRequestDtoToAddress(publisherRequestDto.getAddress()));
  }

  private static Address mapAddressRequestDtoToAddress(final AddressRequestDto addressRequestDto) {
    return new Address(
        addressRequestDto.getHouseNumber(),
        addressRequestDto.getState(),
        addressRequestDto.getCity(),
        addressRequestDto.getZipCode(),
        addressRequestDto.getStreet());
  }

  public static List<Author> mapAuthorRequestDtosToAuthors(
      final List<AuthorRequestDto> authorRequestDtos) {
    return authorRequestDtos.stream()
        .map(BookRestMapper::mapAuthorRequestDtoToAuthor)
        .collect(Collectors.toList());
  }

  private static Author mapAuthorRequestDtoToAuthor(final AuthorRequestDto authorRequestDto) {
    return new Author(
        authorRequestDto.getFirstName(),
        authorRequestDto.getLastName(),
        authorRequestDto.getEmail());
  }

  private static List<AuthorResponseDto> mapAuthorsToAuthorResponseDtos(
      final List<Author> authors) {
    return authors.stream()
        .map(BookRestMapper::mapAuthorToAuthorResponseDto)
        .collect(Collectors.toList());
  }

  private static AuthorResponseDto mapAuthorToAuthorResponseDto(final Author author) {
    var authorResponseDto = new AuthorResponseDto();
    authorResponseDto.setEmail(author.getEmail());
    authorResponseDto.setFirstName(author.getFirstName());
    authorResponseDto.setLastName(author.getLastName());
    return authorResponseDto;
  }
}
