package com.example.graphqlshowcase.adapter.in.dto.request;

import java.io.Serializable;
import javax.validation.constraints.NotNull;

public record AddressRequest(
    @NotNull(message = "street can not be null.") String street,
    String houseNumber,
    @NotNull(message = "City can not be null.") String city,
    @NotNull(message = "State can not be null.") String state,
    @NotNull(message = "zip code can not be null.") String zipCode)
    implements Serializable {}
