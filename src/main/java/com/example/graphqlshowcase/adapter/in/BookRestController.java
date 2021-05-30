package com.example.graphqlshowcase.adapter.in;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.graphqlshowcase.adapter.in.dto.request.CreateBookRequestDto;
import com.example.graphqlshowcase.adapter.in.dto.request.UpdateBookRequestDto;
import com.example.graphqlshowcase.adapter.in.dto.response.BookResponseDto;
import com.example.graphqlshowcase.domain.BookDomainService;
import com.example.graphqlshowcase.domain.entity.Book;
import com.example.graphqlshowcase.domain.valueobject.Genre;
import com.example.graphqlshowcase.domain.valueobject.ISBN;

import lombok.RequiredArgsConstructor;

@RestController
@Validated
@RequiredArgsConstructor
public class BookRestController {
	
	private final BookDomainService bookService;

	@GetMapping(path = "/books/{bookId}")
	public ResponseEntity<BookResponseDto> retieveBookByIsbn(
			final @PathVariable("bookId") String bookId) {
		var result = bookService.retrieveBookById(bookId);
		return new ResponseEntity<>(BookRestMapper.mapBookToBookResponseDto(result),
				HttpStatus.OK);
	}

	@PostMapping(path = "/books", produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookResponseDto> createBook(final @RequestBody @Valid CreateBookRequestDto createBookRequestDto) {
		var authors = BookRestMapper.mapAuthorRequestDtosToAuthors(createBookRequestDto.getAuthors());
		var createdBook = bookService.createBook(new ISBN(createBookRequestDto.getIsbn()),
				Genre.valueOf(createBookRequestDto.getGenre()),
				createBookRequestDto.getTitle(), authors, 
				BookRestMapper.mapPublisherRequestDtoToPublisher(createBookRequestDto.getPublisher()));
		var output = BookRestMapper.mapBookToBookResponseDto(createdBook);
		return new ResponseEntity<BookResponseDto>(output, HttpStatus.CREATED);
	}

	@PutMapping(path = "/books", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookResponseDto> updateBook(
			final @RequestBody @Valid UpdateBookRequestDto updateBookRequestDto) {
		var book = BookRestMapper.mapUpdateBookRequestDtoToBook(updateBookRequestDto);
		boolean wasAcknowledged = bookService.updateBook(book);
		if (wasAcknowledged) {
			var updatedBook = bookService.retrieveBookById(updateBookRequestDto.getId());
			return new ResponseEntity<>(BookRestMapper.mapBookToBookResponseDto(updatedBook), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
	}

}
