package com.example.graphqlshowcase.adapter.in.dto.response;

import java.io.Serializable;

public record AuthorResponse(String firstName, String lastName, String email)
    implements Serializable {}
