package com.example.graphqlshowcase.adapter.in.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDto implements Serializable {
	
	
	private static final long serialVersionUID = -1812607289914319079L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
    private String code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String httpMethod;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String exception;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String path;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "yyyy-MM-ddTHH:mm:SS.ssZ")
    private String timestamp;

}
