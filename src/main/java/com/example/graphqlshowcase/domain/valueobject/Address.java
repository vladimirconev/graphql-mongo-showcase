package com.example.graphqlshowcase.domain.valueobject;

import java.io.Serial;
import java.io.Serializable;

public record Address(String houseNumber, String state, String city, String zipCode, String street)
    implements Serializable {

  @Serial private static final long serialVersionUID = -3830539598164444969L;
}
