package com.example.graphqlshowcase.domain.entity;

import java.io.Serializable;
import java.util.List;

import com.example.graphqlshowcase.domain.valueobject.Author;
import com.example.graphqlshowcase.domain.valueobject.Genre;
import com.example.graphqlshowcase.domain.valueobject.ISBN;
import com.example.graphqlshowcase.domain.valueobject.Publisher;

import lombok.Value;

@Value
public class Book implements Serializable {
	
	private static final long serialVersionUID = 4386647600220620100L;
	
	private String id;
	
	private String title;
	
	private ISBN isbn;
	
	private List<Author> authors;
	
	private Genre genre;
	
	private Publisher publisher;

}
