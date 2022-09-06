package com.example.graphqlshowcase.domain.valueobject;

import java.io.Serializable;
import lombok.Value;

@Value
public class ISBN implements Serializable {

  private static final long serialVersionUID = -8544011886541048273L;

  private final String isbn;
}
