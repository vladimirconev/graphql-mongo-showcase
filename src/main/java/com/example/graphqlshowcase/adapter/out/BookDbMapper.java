package com.example.graphqlshowcase.adapter.out;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.graphqlshowcase.adapter.out.db.dto.AddressMongoDto;
import com.example.graphqlshowcase.adapter.out.db.dto.AuthorMongoDto;
import com.example.graphqlshowcase.adapter.out.db.dto.BookMongoDto;
import com.example.graphqlshowcase.adapter.out.db.dto.PublisherMongoDto;
import com.example.graphqlshowcase.domain.entity.Book;
import com.example.graphqlshowcase.domain.valueobject.Address;
import com.example.graphqlshowcase.domain.valueobject.Author;
import com.example.graphqlshowcase.domain.valueobject.Genre;
import com.example.graphqlshowcase.domain.valueobject.ISBN;
import com.example.graphqlshowcase.domain.valueobject.Publisher;

public class BookDbMapper {
	
	private BookDbMapper() {
		
	}
	
	public static Set<AuthorMongoDto> mapAuthorsToAuthorMongoDtos(final List<Author> authors) {
		return authors.stream().map(BookDbMapper::mapAuthorToAuthorMongoDto).collect(Collectors.toSet());
	}
	
	private static AuthorMongoDto mapAuthorToAuthorMongoDto(final Author author) {
		var authorMongoDto = new AuthorMongoDto();
		authorMongoDto.setEmail(author.getEmail());
		authorMongoDto.setFirstName(author.getFirstName());
		authorMongoDto.setLastName(author.getLastName());
		return authorMongoDto;
	}
	
	private static List<Author> mapAuthorMongoDtosToAuthors(final Set<AuthorMongoDto> authorMongoDtos) {
		return authorMongoDtos.stream()
				.map(BookDbMapper::mapAuthorMongoDtoToAuthor)
				.collect(Collectors.toList());
	}
	
	private static Author mapAuthorMongoDtoToAuthor(final AuthorMongoDto authorMongoDto) {
		return new Author(authorMongoDto.getFirstName(),
				authorMongoDto.getLastName(), authorMongoDto.getEmail());
	}
	
	public static Book mapBookMongoDtoToBook(final BookMongoDto bookMongoDto) {
		return new Book(bookMongoDto.getId(), 
				bookMongoDto.getTitle(), 
				new ISBN(bookMongoDto.getIsbn()),
				mapAuthorMongoDtosToAuthors(bookMongoDto.getAuthors()),
				Genre.valueOf(bookMongoDto.getGenre()),
				mapPublisherMongoDtoToPublisher(bookMongoDto.getPublisher()));
	}
	
	private static Publisher mapPublisherMongoDtoToPublisher(final PublisherMongoDto publisherMongoDto) {
		return new Publisher(publisherMongoDto.getName(), 
				mapAddressMongoDtoToAddress(publisherMongoDto.getAddress()));
	}
	
	private static Address mapAddressMongoDtoToAddress(final AddressMongoDto addressMongoDto) {
		return new Address(addressMongoDto.getHouseNumber(),
				addressMongoDto.getState(),
				addressMongoDto.getCity(),
				addressMongoDto.getZipCode(),
				addressMongoDto.getStreet());
	}
	
	public static BookMongoDto mapBookToBookMongoDto(final Book book) {
		return BookMongoDto.builder().genre(book.getGenre().name()).authors(null).title(book.getTitle())
				.isbn(book.getIsbn().getIsbn()).build();
	}
	
	public static PublisherMongoDto mapPublisherToPublisherMongoDto(final Publisher publisher) {
		var publisherMongoDto = new PublisherMongoDto();
		publisherMongoDto.setName(publisher.getName());
		publisherMongoDto.setAddress(mapAddressToAddressMongoDto(publisher.getAddress()));
		return publisherMongoDto;
	}
	
	private static AddressMongoDto mapAddressToAddressMongoDto(final Address address) {
		var addressMongoDto = new AddressMongoDto();
		addressMongoDto.setCity(address.getCity());
		addressMongoDto.setHouseNumber(address.getHouseNumber());
		addressMongoDto.setState(address.getState());
		addressMongoDto.setStreet(address.getStreet());
		addressMongoDto.setZipCode(address.getZipCode());
		return addressMongoDto;
	}

}
