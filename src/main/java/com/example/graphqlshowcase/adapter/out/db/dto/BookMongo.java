package com.example.graphqlshowcase.adapter.out.db.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;
import org.springframework.data.annotation.Id;

public record BookMongo(
    @Id String id,
    String isbn,
    String genre,
    String title,
    Set<AuthorMongo> authors,
    PublisherMongo publisher,
    Instant creationDate,
    Instant lastUpdateDate)
    implements Serializable {}
