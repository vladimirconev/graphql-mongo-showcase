package com.example.graphqlshowcase.domain.valueobject;

import java.io.Serializable;

import lombok.Value;

@Value
public class Publisher implements Serializable {

	private static final long serialVersionUID = -4981490988152504647L;

	private final String name;
	
	private final Address address;
	
}
