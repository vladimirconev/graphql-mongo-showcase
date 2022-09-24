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
public class AuthorMongo implements Serializable {

  private static final long serialVersionUID = -5174940638387804658L;

  private String firstName;

  private String lastName;

  private String email;
}
