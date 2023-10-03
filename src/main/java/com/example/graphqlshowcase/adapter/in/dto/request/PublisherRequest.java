package com.example.graphqlshowcase.adapter.in.dto.request;

import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public record PublisherRequest(
    @NotNull(message = "Publisher name can not be null.") String name,
    @Valid AddressRequest address)
    implements Serializable {}
