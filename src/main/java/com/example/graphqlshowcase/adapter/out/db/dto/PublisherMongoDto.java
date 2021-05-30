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
public class PublisherMongoDto implements Serializable {
	
	private static final long serialVersionUID = -1360153551725166756L;

	private String name;
	
	private AddressMongoDto address;

}
