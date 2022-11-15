package com.example.graphqlshowcase.adapter.in.dto.request;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateBookRequest implements Serializable {

  @Serial private static final long serialVersionUID = 4656640694464246801L;

  @NotNull(message = "ISBN can not be null.")
  private String isbn;

  @NotNull(message = "Title can not be null.")
  private String title;

  @NotNull(message = "Authors can not be null.")
  @Valid
  private List<AuthorRequest> authors;

  @NotNull(message = "Genre can not be null.")
  @Pattern(
      regexp =
          "SCI_FI"
              + "|FANTASY"
              + "|OTHER"
              + "|MYSTERY"
              + "|THRILLER"
              + "|ROMANCE"
              + "|WESTERNS"
              + "|DYSTOPIAN"
              + "|CONTEMPORARY",
      message =
          "Genre must have one of the following values:"
              + "SCI_FI"
              + "|FANTASY"
              + "|OTHER"
              + "|MYSTERY"
              + "|THRILLER"
              + "|ROMANCE"
              + "|WESTERNS"
              + "|DYSTOPIAN"
              + "|CONTEMPORARY.")
  private String genre;

  @NotNull(message = "Publisher can not be null.")
  private PublisherRequest publisher;
}
