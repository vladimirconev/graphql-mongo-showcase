package com.example.graphqlshowcase.adapter.out;

import com.example.graphqlshowcase.adapter.out.db.dto.BookMongo;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class BookDataFetcher implements DataFetcher<Page<BookMongo>> {

  private static final String SIZE = "size";
  private static final String OFFSET = "offset";

  private final MongoOperations mongoOperations;

  @Override
  public Page<BookMongo> get(DataFetchingEnvironment environment) {
    Integer size = environment.getArgument(SIZE);
    Integer offset = environment.getArgument(OFFSET);
    PageRequest pageRequest = PageRequest.of(offset, size);
    Query query = new Query();
    query.with(pageRequest);
    List<BookMongo> data = mongoOperations.findAll(BookMongo.class, "books");
    return PageableExecutionUtils.getPage(
        data, pageRequest, () -> mongoOperations.count(query.limit(-1).skip(-1), BookMongo.class));
  }
}
