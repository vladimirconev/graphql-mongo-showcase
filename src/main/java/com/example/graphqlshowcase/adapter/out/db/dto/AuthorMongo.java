package com.example.graphqlshowcase.adapter.out.db.dto;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

@Data
public class AuthorMongo implements Serializable {

  @Serial private static final long serialVersionUID = -5174940638387804658L;

  private String firstName;

  private String lastName;

  private String email;
}
