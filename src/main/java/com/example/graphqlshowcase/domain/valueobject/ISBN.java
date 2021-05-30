package com.example.graphqlshowcase.domain.valueobject;

import lombok.Value;

import java.io.Serializable;

@Value
public class ISBN implements Serializable {
	
	private static final long serialVersionUID = -8544011886541048273L;
	
	private final String isbn;

}
