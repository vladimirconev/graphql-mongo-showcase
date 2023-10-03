package com.example.graphqlshowcase.adapter.out.db.dto;

import java.io.Serializable;

public record AddressMongo(
    String city, String state, String houseNumber, String zipCode, String street)
    implements Serializable {}
