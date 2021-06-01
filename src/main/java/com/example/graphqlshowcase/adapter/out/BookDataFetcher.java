package com.example.graphqlshowcase.adapter.out;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;

import com.example.graphqlshowcase.adapter.out.db.dto.BookMongoDto;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookDataFetcher implements DataFetcher<Page<BookMongoDto>> {

	private static final String SIZE = "size";
	private static final String OFFSET = "offset";

	private final MongoOperations mongoOperations;

	@Override
	public Page<BookMongoDto> get(DataFetchingEnvironment environment) {
		Integer size = environment.getArgument(SIZE);
		Integer offset = environment.getArgument(OFFSET);
		PageRequest pageRequest = PageRequest.of(offset, size);
		Query query = new Query();
		query.with(pageRequest);
		List<BookMongoDto> data = mongoOperations.findAll(BookMongoDto.class, "books");
		return PageableExecutionUtils.getPage(data, pageRequest,
				() -> mongoOperations.count(query.limit(-1).skip(-1), BookMongoDto.class));
	}

}
