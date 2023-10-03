package com.example.graphqlshowcase.adapter.in.dto.response;

import java.io.Serializable;

public record AddressResponse(
    String state, String city, String houseNumber, String street, String zipCode)
    implements Serializable {}
