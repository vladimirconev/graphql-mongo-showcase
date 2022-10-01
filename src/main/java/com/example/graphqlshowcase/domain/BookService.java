package com.example.graphqlshowcase.domain;

import com.example.graphqlshowcase.domain.entity.Book;
import com.example.graphqlshowcase.domain.valueobject.Author;
import com.example.graphqlshowcase.domain.valueobject.Genre;
import com.example.graphqlshowcase.domain.valueobject.ISBN;
import com.example.graphqlshowcase.domain.valueobject.Publisher;
import java.util.List;

public class BookService implements BookDomainService {

  private final BookRepository bookRepository;

  public BookService(final BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Override
  public Book createBook(
      ISBN isbn, Genre genre, String title, List<Author> authors, Publisher publisher) {
    return bookRepository.createBook(isbn, genre, title, authors, publisher);
  }

  @Override
  public boolean updateBook(Book book) {
    return bookRepository.updateBook(book);
  }

  @Override
  public Book retrieveBookById(String id) {
    return bookRepository.retrieveBookById(id);
  }
}
