package com.example.graphqlshowcase.adapter.out;

import com.example.graphqlshowcase.adapter.out.db.dto.BookMongo;
import com.example.graphqlshowcase.domain.BookRepository;
import com.example.graphqlshowcase.domain.entity.Book;
import com.example.graphqlshowcase.domain.valueobject.Author;
import com.example.graphqlshowcase.domain.valueobject.Genre;
import com.example.graphqlshowcase.domain.valueobject.ISBN;
import com.example.graphqlshowcase.domain.valueobject.Publisher;
import com.mongodb.client.result.UpdateResult;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
public class MongoBookRepository implements BookRepository {

  private static final String BOOKS_COLLECTION_NAME = "books";

  private final MongoOperations mongoOperations;

  public MongoBookRepository(final MongoOperations mongoOperations) {
    this.mongoOperations = mongoOperations;
  }

  @Override
  public Book createBook(
      final ISBN isbn,
      final Genre genre,
      final String title,
      final List<Author> authors,
      final Publisher publisher) {
    var savedBook =
        mongoOperations.save(
            BookMongo.builder()
                .genre(genre.name())
                .title(title)
                .isbn(isbn.isbn())
                .authors(BookDbMapper.mapAuthorsToAuthorMongoDtos(authors))
                .publisher(BookDbMapper.mapPublisherToPublisherMongo(publisher))
                .creationDate(LocalDateTime.now(Clock.systemUTC()))
                .build(),
            BOOKS_COLLECTION_NAME);
    return BookDbMapper.mapBookMongoToBook(savedBook);
  }

  @Override
  public boolean updateBook(final Book book) {
    Update update = new Update();
    update.set("title", book.title());
    update.set("authors", BookDbMapper.mapAuthorsToAuthorMongoDtos(book.authors()));
    update.set("isbn", book.isbn());
    update.set("publisher", BookDbMapper.mapPublisherToPublisherMongo(book.publisher()));
    update.set("genre", book.genre().name());
    update.set("lastUpdateDate", LocalDateTime.now());
    UpdateResult updateResult =
        mongoOperations.updateFirst(
            new Query().addCriteria(Criteria.where("id").is(book.id())),
            update,
            BookMongo.class,
            BOOKS_COLLECTION_NAME);
    return updateResult.wasAcknowledged();
  }

  @Override
  public Book retrieveBookById(final String id) {
    return Optional.ofNullable(
            mongoOperations.findOne(
                new Query().addCriteria(Criteria.where("id").is(id)),
                BookMongo.class,
                BOOKS_COLLECTION_NAME))
        .map(BookDbMapper::mapBookMongoToBook)
        .orElseThrow(
            () -> new NoSuchElementException("Book with ID: %s is Not found.".formatted(id)));
  }

  @Override
  public void deleteAllBooks() {
    if (mongoOperations.collectionExists(BOOKS_COLLECTION_NAME)) {
      mongoOperations.dropCollection(BOOKS_COLLECTION_NAME);
    }
  }
}
