package com.example.graphqlshowcase.adapter.in.dto.response;

import java.io.Serializable;

import lombok.Data;

@Data
public class PublisherResponseDto implements Serializable {
	
	private static final long serialVersionUID = 9059761264216237720L;

	private String name;
	
	private AddressResponseDto address;

}
