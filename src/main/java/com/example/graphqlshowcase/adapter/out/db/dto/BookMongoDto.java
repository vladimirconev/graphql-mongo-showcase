package com.example.graphqlshowcase.adapter.out.db.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class BookMongoDto implements Serializable {

  private static final long serialVersionUID = 5879593082798937046L;

  @Id private String id;

  private String isbn;

  private String genre;

  private String title;

  private Set<AuthorMongoDto> authors;

  private PublisherMongoDto publisher;

  private LocalDateTime creationDate;

  private LocalDateTime lastUpdateDate;
}
