package com.example.graphqlshowcase.adapter.out.db.dto;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class AddressMongoDto implements Serializable {

  private static final long serialVersionUID = -2938052011869723591L;

  private String city;

  private String state;

  private String houseNumber;

  private String zipCode;

  private String street;
}
