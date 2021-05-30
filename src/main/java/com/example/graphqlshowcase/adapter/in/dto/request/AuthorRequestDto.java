package com.example.graphqlshowcase.adapter.in.dto.request;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AuthorRequestDto implements Serializable {

	private static final long serialVersionUID = 4232016275632359748L;

	@NotNull(message = "first name can not be null.")
	private String firstName;
	
	@NotNull(message = "last name can not be null. ")
	private String lastName;
	
	@Email(message = "E-mail is not valid.")
	private String email;
	
}
