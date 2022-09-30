package com.example.graphqlshowcase.domain.valueobject;

import java.io.Serial;
import java.io.Serializable;

public record Author(String firstName, String lastName, String email) implements Serializable {

  @Serial
  private static final long serialVersionUID = 3225853817761988505L;
}
