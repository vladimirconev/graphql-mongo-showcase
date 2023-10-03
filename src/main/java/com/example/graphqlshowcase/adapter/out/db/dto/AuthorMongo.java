package com.example.graphqlshowcase.adapter.out.db.dto;

import java.io.Serializable;

public record AuthorMongo(String firstName, String lastName, String email)
    implements Serializable {}
