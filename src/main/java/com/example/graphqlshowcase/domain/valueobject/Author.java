package com.example.graphqlshowcase.domain.valueobject;

import java.io.Serializable;
import lombok.Value;

@Value
public class Author implements Serializable {

  private static final long serialVersionUID = 3225853817761988505L;

  private final String firstName;

  private final String lastName;

  private final String email;
}
