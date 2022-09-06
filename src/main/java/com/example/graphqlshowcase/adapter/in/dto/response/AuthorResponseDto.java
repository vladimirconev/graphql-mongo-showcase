package com.example.graphqlshowcase.adapter.in.dto.response;

import java.io.Serializable;
import lombok.Data;

@Data
public class AuthorResponseDto implements Serializable {

  private static final long serialVersionUID = 7677769326596733712L;

  private String firstName;

  private String lastName;

  private String email;
}
