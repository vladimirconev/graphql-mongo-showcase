package com.example.graphqlshowcase.adapter.out;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.example.graphqlshowcase.adapter.out.db.dto.BookMongoDto;
import com.example.graphqlshowcase.domain.BookRepository;
import com.example.graphqlshowcase.domain.entity.Book;
import com.example.graphqlshowcase.domain.valueobject.Author;
import com.example.graphqlshowcase.domain.valueobject.Genre;
import com.example.graphqlshowcase.domain.valueobject.ISBN;
import com.example.graphqlshowcase.domain.valueobject.Publisher;
import com.mongodb.client.result.UpdateResult;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MongoBookRepository implements BookRepository {
	
	private final static String BOOKS_COLLECTION_NAME ="books";

	private final MongoOperations mongoOperations;
	
	@Override
	public Book createBook(final ISBN isbn,
			final Genre genre, final String title, 
			final List<Author> authors,
			final Publisher publisher) {
		var bookMongoDto = BookMongoDto.builder().genre(genre.name()).title(title).isbn(isbn.getIsbn())
		.authors(BookDbMapper.mapAuthorsToAuthorMongoDtos(authors))
		.publisher(BookDbMapper.mapPublisherToPublisherMongoDto(publisher)).build();
		BookMongoDto savedBookMongoDto = mongoOperations.save(bookMongoDto, BOOKS_COLLECTION_NAME);
		return BookDbMapper.mapBookMongoDtoToBook(savedBookMongoDto);
	}

	@Override
	public boolean updateBook(final Book book) {
		Update update = new Update();
		update.set("title", book.getTitle());
		update.set("authors", BookDbMapper.mapAuthorsToAuthorMongoDtos(book.getAuthors()));
		update.set("isbn", book.getIsbn().getIsbn());
		update.set("publisher", BookDbMapper.mapPublisherToPublisherMongoDto(book.getPublisher()));
		update.set("genre", book.getGenre().name());
		UpdateResult updateResult = mongoOperations.updateFirst(
				new Query().addCriteria(Criteria.where("id").is(book.getId())), update, BookMongoDto.class, BOOKS_COLLECTION_NAME);
		return updateResult.wasAcknowledged();
	}

	@Override
	public Book retrieveBookById(final String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		var bookMongoDto = mongoOperations.findOne(query, BookMongoDto.class, BOOKS_COLLECTION_NAME);
		return BookDbMapper.mapBookMongoDtoToBook(bookMongoDto);
	}

	
}
