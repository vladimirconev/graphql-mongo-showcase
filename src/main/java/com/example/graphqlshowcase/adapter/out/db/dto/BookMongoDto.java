package com.example.graphqlshowcase.adapter.out.db.dto;

import java.io.Serializable;
import java.util.Set;

import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class BookMongoDto implements Serializable {
	
	private static final long serialVersionUID = 5879593082798937046L;

	@Id
	private String id;
	
	private String isbn;
	
	private String genre;
	
	private String title;
	
	private Set<AuthorMongoDto> authors;
	
	private PublisherMongoDto publisher;
	

}
