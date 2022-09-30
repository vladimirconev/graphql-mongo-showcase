package com.example.graphqlshowcase.domain.entity;

import com.example.graphqlshowcase.domain.valueobject.Author;
import com.example.graphqlshowcase.domain.valueobject.Genre;
import com.example.graphqlshowcase.domain.valueobject.ISBN;
import com.example.graphqlshowcase.domain.valueobject.Publisher;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public record Book(
    String id, String title, ISBN isbn, List<Author> authors, Genre genre, Publisher publisher)
    implements Serializable {

  @Serial
  private static final long serialVersionUID = 4386647600220620100L;
}
