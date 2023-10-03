package com.example.graphqlshowcase.adapter.out.db.dto;

import java.io.Serializable;

public record PublisherMongo(String name, AddressMongo address) implements Serializable {}
