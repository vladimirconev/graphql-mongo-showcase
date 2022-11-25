package com.example.graphqlshowcase.adapter.out.db.dto;

import java.io.Serial;
import java.io.Serializable;
import lombok.*;

@Data
public class PublisherMongo implements Serializable {

  @Serial
  private static final long serialVersionUID = -1360153551725166756L;

  private String name;

  private AddressMongo address;
}
