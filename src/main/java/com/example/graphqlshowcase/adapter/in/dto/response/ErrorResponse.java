package com.example.graphqlshowcase.adapter.in.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse implements Serializable {

  private static final long serialVersionUID = -1812607289914319079L;

  private String code;

  private String status;

  private String httpMethod;

  private String exception;

  private String path;

  private String message;

  @JsonFormat(pattern = "yyyy-MM-ddTHH:mm:SS.ssZ")
  private String timestamp;
}
