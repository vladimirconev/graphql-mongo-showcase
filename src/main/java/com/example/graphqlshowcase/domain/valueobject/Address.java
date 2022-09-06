package com.example.graphqlshowcase.domain.valueobject;

import java.io.Serializable;
import lombok.Value;

@Value
public class Address implements Serializable {

  private static final long serialVersionUID = -3830539598164444969L;

  private final String houseNumber;

  private final String state;

  private final String city;

  private final String zipCode;

  private final String street;
}
