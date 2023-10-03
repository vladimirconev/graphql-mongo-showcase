package com.example.graphqlshowcase.adapter.in.dto.response;

import java.io.Serializable;

public record PublisherResponse(String name, AddressResponse address) implements Serializable {}
