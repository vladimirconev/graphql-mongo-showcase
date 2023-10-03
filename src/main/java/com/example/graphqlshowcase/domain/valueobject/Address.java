package com.example.graphqlshowcase.domain.valueobject;

import java.io.Serializable;

public record Address(String houseNumber, String state, String city, String zipCode, String street)
    implements Serializable {}
