package com.example.graphqlshowcase.adapter.in.dto.response;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class BookResponseDto implements Serializable {

  private static final long serialVersionUID = -2459078227556872842L;

  private String id;

  private String isbn;

  private String title;

  private List<AuthorResponseDto> authors;

  private String genre;

  private PublisherResponseDto publisher;
}
