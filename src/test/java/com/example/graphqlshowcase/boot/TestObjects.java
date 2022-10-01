package com.example.graphqlshowcase.boot;

import com.example.graphqlshowcase.adapter.in.dto.request.*;
import com.example.graphqlshowcase.domain.valueobject.Genre;
import java.util.List;

public final class TestObjects {

  private TestObjects() {}

  public static CreateBookRequest createBookRequest() {
    var createRequestPayload = new CreateBookRequest();
    createRequestPayload.setTitle("TEST");
    createRequestPayload.setIsbn("978-1-891830-25-9");
    createRequestPayload.setGenre(Genre.SCI_FI.name());
    var publisher = new PublisherRequest();
    publisher.setName("PEARSON");
    var address = new AddressRequest();
    address.setCity("London");
    address.setStreet("88 Park Road");
    address.setZipCode("EC1A");
    address.setState("United Kingdom");
    address.setHouseNumber("23A");
    publisher.setAddress(address);

    createRequestPayload.setPublisher(publisher);

    var author = new AuthorRequest();
    author.setEmail("elaine.bloyd@pearson.com");
    author.setFirstName("Elaine");
    author.setLastName("Bloyd");
    createRequestPayload.setAuthors(List.of(author));

    return createRequestPayload;
  }

  public static UpdateBookRequest updateBookRequestByTitleAndGenre(
      final String title, final String genre) {
    var updateBookRequest = new UpdateBookRequest();
    updateBookRequest.setTitle(title);
    updateBookRequest.setGenre(genre);
    updateBookRequest.setIsbn("978-1-891830-25-9");
    var publisher = new PublisherRequest();
    publisher.setName("PEARSON");
    var address = new AddressRequest();
    address.setCity("London");
    address.setStreet("88 Park Road");
    address.setZipCode("EC1A");
    address.setState("United Kingdom");
    address.setHouseNumber("23A");
    publisher.setAddress(address);
    updateBookRequest.setPublisher(publisher);

    var author = new AuthorRequest();
    author.setEmail("elaine.bloyd@pearson.com");
    author.setFirstName("Elaine");
    author.setLastName("Bloyd");
    updateBookRequest.setAuthors(List.of(author));

    return updateBookRequest;
  }
}
