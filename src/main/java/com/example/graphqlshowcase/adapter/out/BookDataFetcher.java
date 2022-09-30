package com.example.graphqlshowcase.adapter.out;

import com.example.graphqlshowcase.adapter.out.db.dto.BookMongo;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;

public class BookDataFetcher implements DataFetcher<Page<BookMongo>> {

  private static final String SIZE = "size";
  private static final String OFFSET = "offset";
  private static final String COLLECTION_NAME = "books";

  private final MongoOperations mongoOperations;

  public BookDataFetcher(final MongoOperations mongoOperations) {
    this.mongoOperations = mongoOperations;
  }

  @Override
  public Page<BookMongo> get(DataFetchingEnvironment environment) {
    Integer size = environment.getArgument(SIZE);
    Integer offset = environment.getArgument(OFFSET);
    PageRequest pageRequest = PageRequest.of(offset, size);
    Query query = new Query();
    query.with(pageRequest);
    List<BookMongo> data = mongoOperations.findAll(BookMongo.class, COLLECTION_NAME);
    return PageableExecutionUtils.getPage(
        data, pageRequest, () -> mongoOperations.count(query.limit(-1).skip(-1), BookMongo.class));
  }
}
