package com.example.graphqlshowcase.domain;

import java.util.List;

import com.example.graphqlshowcase.domain.entity.Book;
import com.example.graphqlshowcase.domain.valueobject.Author;
import com.example.graphqlshowcase.domain.valueobject.Genre;
import com.example.graphqlshowcase.domain.valueobject.ISBN;
import com.example.graphqlshowcase.domain.valueobject.Publisher;

public interface BookRepository {
	
	Book createBook(final ISBN isbn, final Genre genre, final String title,
			final List<Author> authors, final Publisher publisher);

	boolean updateBook(final Book book);

	Book retrieveBookById(final String id);
	
	void deleteAllBooks();

}
