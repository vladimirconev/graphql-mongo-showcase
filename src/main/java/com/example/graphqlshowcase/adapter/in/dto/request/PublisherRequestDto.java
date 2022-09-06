package com.example.graphqlshowcase.adapter.in.dto.request;

import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PublisherRequestDto implements Serializable {

  private static final long serialVersionUID = -2568025365962170421L;

  @NotNull(message = "Publisher name can not be null.")
  private String name;

  @Valid private AddressRequestDto address;
}
