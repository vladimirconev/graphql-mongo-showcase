package com.example.graphqlshowcase.domain.valueobject;

import java.io.Serial;
import java.io.Serializable;

public record Publisher(String name, Address address) implements Serializable {

  @Serial private static final long serialVersionUID = -4981490988152504647L;
}
