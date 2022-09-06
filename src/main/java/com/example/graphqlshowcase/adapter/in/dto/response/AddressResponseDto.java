package com.example.graphqlshowcase.adapter.in.dto.response;

import java.io.Serializable;
import lombok.Data;

@Data
public class AddressResponseDto implements Serializable {

  private static final long serialVersionUID = 4262302659771225934L;

  private String state;

  private String city;

  private String houseNumber;

  private String street;

  private String zipCode;
}
