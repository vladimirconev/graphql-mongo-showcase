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
    return new AuthorMongo(author.firstName(), author.lastName(), author.email());
  }

  private static List<Author> mapAuthorMongoDtosToAuthors(final Set<AuthorMongo> authorMongos) {
    return authorMongos.stream()
        .map(BookDbMapper::mapAuthorMongoToAuthor)
        .collect(Collectors.toList());
  }

  private static Author mapAuthorMongoToAuthor(final AuthorMongo authorMongo) {
    return new Author(authorMongo.firstName(), authorMongo.lastName(), authorMongo.email());
  }

  public static Book mapBookMongoToBook(final BookMongo bookMongo) {
    return new Book(
        bookMongo.id(),
        bookMongo.title(),
        new ISBN(bookMongo.isbn()),
        mapAuthorMongoDtosToAuthors(bookMongo.authors()),
        Genre.valueOf(bookMongo.genre()),
        mapPublisherMongoDtoToPublisher(bookMongo.publisher()));
  }

  private static Publisher mapPublisherMongoDtoToPublisher(final PublisherMongo publisherMongo) {
    return new Publisher(publisherMongo.name(), mapAddressMongoToAddress(publisherMongo.address()));
  }

  private static Address mapAddressMongoToAddress(final AddressMongo addressMongo) {
    return new Address(
        addressMongo.houseNumber(),
        addressMongo.state(),
        addressMongo.city(),
        addressMongo.zipCode(),
        addressMongo.street());
  }

  public static PublisherMongo mapPublisherToPublisherMongo(final Publisher publisher) {
    return new PublisherMongo(publisher.name(), mapAddressToAddressMongo(publisher.address()));
  }

  private static AddressMongo mapAddressToAddressMongo(final Address address) {
    return new AddressMongo(
        address.city(),
        address.state(),
        address.houseNumber(),
        address.zipCode(),
        address.street());
  }
}
