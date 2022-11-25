package com.example.graphqlshowcase.adapter.out.db.dto;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.*;
import org.springframework.data.annotation.Id;

@Builder
@Data
public class BookMongo implements Serializable {

  @Serial
  private static final long serialVersionUID = 5879593082798937046L;

  @Id private String id;

  private String isbn;

  private String genre;

  private String title;

  private Set<AuthorMongo> authors;

  private PublisherMongo publisher;

  private LocalDateTime creationDate;

  private LocalDateTime lastUpdateDate;
}
