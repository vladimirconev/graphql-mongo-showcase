package com.example.graphqlshowcase.domain.valueobject;

import java.io.Serializable;

public record Author(String firstName, String lastName, String email) implements Serializable {}
