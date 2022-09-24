package com.example.graphqlshowcase.adapter.in.dto.request;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddressRequest implements Serializable {

  private static final long serialVersionUID = -298078024599143738L;

  @NotNull(message = "street can not be null.")
  private String street;

  private String houseNumber;

  @NotNull(message = "City can not be null.")
  private String city;

  @NotNull(message = "State can not be null.")
  private String state;

  @NotNull(message = "zip code can not be null.")
  private String zipCode;
}
