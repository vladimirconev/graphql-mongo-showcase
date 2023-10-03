package com.example.graphqlshowcase.boot;

import com.example.graphqlshowcase.adapter.in.dto.request.*;
import com.example.graphqlshowcase.domain.valueobject.Genre;
import java.util.List;

public final class TestObjects {

  private TestObjects() {}

  public static CreateBookRequest createBookRequest() {
    var address = new AddressRequest("88 Park Road", "23A", "London", "United Kingdom", "EC1A");
    var author = new AuthorRequest("Elaine", "Bloyd", "elaine.bloyd@pearson.com");
    var publisher = new PublisherRequest("PEARSON", address);
    return new CreateBookRequest(
        "978-1-891830-25-9", "TEST", List.of(author), Genre.SCI_FI.name(), publisher);
  }

  public static UpdateBookRequest updateBookRequestByTitleAndGenre(
      final String title, final String genre) {
    var author = new AuthorRequest("Elaine", "Bloyd", "elaine.bloyd@pearson.com");
    var address = new AddressRequest("88 Park Road", "23A", "London", "United Kingdom", "EC1A");
    var publisher = new PublisherRequest("PEARSON", address);
    return new UpdateBookRequest("978-1-891830-25-9", title, List.of(author), genre, publisher);
  }
}
