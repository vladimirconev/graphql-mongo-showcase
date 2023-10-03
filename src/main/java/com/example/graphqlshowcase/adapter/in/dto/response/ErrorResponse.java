package com.example.graphqlshowcase.adapter.in.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
    String code,
    String status,
    String httpMethod,
    String exception,
    String path,
    String message,
    Instant timestamp)
    implements Serializable {}
