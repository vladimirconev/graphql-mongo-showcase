package com.example.graphqlshowcase.adapter.in.dto.response;

import java.io.Serializable;
import java.util.List;

public record BookResponse(
    String id,
    String isbn,
    String title,
    List<AuthorResponse> authors,
    String genre,
    PublisherResponse publisher)
    implements Serializable {}
