package com.example.graphqlshowcase.adapter.out.db.dto;

import java.io.Serial;
import java.io.Serializable;
import lombok.*;

@Data
public class AddressMongo implements Serializable {

  @Serial private static final long serialVersionUID = -2938052011869723591L;

  private String city;

  private String state;

  private String houseNumber;

  private String zipCode;

  private String street;
}
