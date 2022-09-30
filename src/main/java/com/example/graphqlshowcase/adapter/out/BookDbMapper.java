package com.example.graphqlshowcase.adapter.out;

import com.example.graphqlshowcase.adapter.out.db.dto.AddressMongo;
import com.example.graphqlshowcase.adapter.out.db.dto.AuthorMongo;
import com.example.graphqlshowcase.adapter.out.db.dto.BookMongo;
import com.example.graphqlshowcase.adapter.out.db.dto.PublisherMongo;
import com.example.graphqlshowcase.domain.entity.Book;
import com.example.graphqlshowcase.domain.valueobject.Address;
import com.example.graphqlshowcase.domain.valueobject.Author;
import com.example.graphqlshowcase.domain.valueobject.Genre;
import com.example.graphqlshowcase.domain.valueobject.ISBN;
import com.example.graphqlshowcase.domain.valueobject.Publisher;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BookDbMapper {

  private BookDbMapper() {}

  public static Set<AuthorMongo> mapAuthorsToAuthorMongoDtos(final List<Author> authors) {
    return authors.stream().map(BookDbMapper::mapAuthorToAuthorMongo).collect(Collectors.toSet());
  }

  private static AuthorMongo mapAuthorToAuthorMongo(final Author author) {
    var authorMongoDto = new AuthorMongo();
    authorMongoDto.setEmail(author.email());
    authorMongoDto.setFirstName(author.firstName());
    authorMongoDto.setLastName(author.lastName());
    return authorMongoDto;
  }

  private static List<Author> mapAuthorMongoDtosToAuthors(final Set<AuthorMongo> authorMongos) {
    return authorMongos.stream()
        .map(BookDbMapper::mapAuthorMongoToAuthor)
        .collect(Collectors.toList());
  }

  private static Author mapAuthorMongoToAuthor(final AuthorMongo authorMongo) {
    return new Author(
        authorMongo.getFirstName(), authorMongo.getLastName(), authorMongo.getEmail());
  }

  public static Book mapBookMongoToBook(final BookMongo bookMongo) {
    return new Book(
        bookMongo.getId(),
        bookMongo.getTitle(),
        new ISBN(bookMongo.getIsbn()),
        mapAuthorMongoDtosToAuthors(bookMongo.getAuthors()),
        Genre.valueOf(bookMongo.getGenre()),
        mapPublisherMongoDtoToPublisher(bookMongo.getPublisher()));
  }

  private static Publisher mapPublisherMongoDtoToPublisher(final PublisherMongo publisherMongo) {
    return new Publisher(
        publisherMongo.getName(), mapAddressMongoToAddress(publisherMongo.getAddress()));
  }

  private static Address mapAddressMongoToAddress(final AddressMongo addressMongo) {
    return new Address(
        addressMongo.getHouseNumber(),
        addressMongo.getState(),
        addressMongo.getCity(),
        addressMongo.getZipCode(),
        addressMongo.getStreet());
  }

  public static BookMongo mapBookToBookMongO(final Book book) {
    return BookMongo.builder()
        .genre(book.genre().name())
        .authors(null)
        .title(book.title())
        .isbn(book.isbn().isbn())
        .build();
  }

  public static PublisherMongo mapPublisherToPublisherMongo(final Publisher publisher) {
    var publisherMongo = new PublisherMongo();
    publisherMongo.setName(publisher.name());
    publisherMongo.setAddress(mapAddressToAddressMongo(publisher.address()));
    return publisherMongo;
  }

  private static AddressMongo mapAddressToAddressMongo(final Address address) {
    var addressMongo = new AddressMongo();
    addressMongo.setCity(address.city());
    addressMongo.setHouseNumber(address.houseNumber());
    addressMongo.setState(address.state());
    addressMongo.setStreet(address.street());
    addressMongo.setZipCode(address.zipCode());
    return addressMongo;
  }
}
