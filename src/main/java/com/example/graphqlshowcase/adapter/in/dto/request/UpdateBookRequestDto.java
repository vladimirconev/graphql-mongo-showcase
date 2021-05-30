package com.example.graphqlshowcase.adapter.in.dto.request;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class UpdateBookRequestDto implements Serializable {

	private static final long serialVersionUID = 4656640694464246801L;
	
	@NotNull(message = "Id can not be null.")
	private String id;

	@NotNull(message = "ISBN can not be null.")
	private String isbn;

	@NotNull(message = "Title can not be null.")
	private String title;

	@NotNull(message = "Authors can not be null.")
	@Valid
	private List<AuthorRequestDto> authors;

	@NotNull(message = "Genre can not be null.")
	@Pattern(regexp = "SCI_FI" + "|FANTASY" + "|OTHER" + "|MYSTERY" + "|THRILLER" + "|ROMANCE" + "|WESTERNS"
			+ "|DYSTOPIANS" + "|CONTEMPORARY", message = "Genre must have one of the following values:" + "SCI_FI"
					+ "|FANTASY" + "|OTHER" + "|MYSTERY" + "|THRILLER" + "|ROMANCE" + "|WESTERNS" + "|DYSTOPIANS"
					+ "|CONTEMPORARY.")
	private String genre;
	
	@NotNull(message = "Publisher can not be null.")
	private PublisherRequestDto publisher;
}
