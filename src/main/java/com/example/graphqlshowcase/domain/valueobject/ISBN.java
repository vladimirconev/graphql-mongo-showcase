package com.example.graphqlshowcase.domain.valueobject;

import java.io.Serial;
import java.io.Serializable;

public record ISBN(String isbn) implements Serializable {

  @Serial
  private static final long serialVersionUID = -8544011886541048273L;
}
