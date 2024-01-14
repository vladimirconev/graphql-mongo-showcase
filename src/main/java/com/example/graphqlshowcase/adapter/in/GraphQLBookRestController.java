package com.example.graphqlshowcase.adapter.in;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.GraphQLError;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Books GraphQL")
public class GraphQLBookRestController {

  private static final String QUERY = "query";
  private static final String VARIABLES = "variables";

  private final GraphQL graphQl;

  public GraphQLBookRestController(final GraphQL graphQl) {
    this.graphQl = graphQl;
  }

  @PostMapping(value = "/graphql/books")
  public ResponseEntity<?> retrieveBooks(@RequestBody Map<String, Object> body) {
    @SuppressWarnings("unchecked")
    final Map<String, Object> variables = (Map<String, Object>) body.get(VARIABLES);
    final String query = (String) body.get(QUERY);
    ExecutionInput executionInput =
        ExecutionInput.newExecutionInput().query(query).variables(variables).build();
    ExecutionResult executionResult = graphQl.execute(executionInput);
    checkErrors(executionResult.getErrors());
    return ResponseEntity.ok(executionResult.getData());
  }

  private void checkErrors(final List<GraphQLError> errors) {
    if (!CollectionUtils.isEmpty(errors)) {
      throw new IllegalStateException(
          errors.stream().map(GraphQLError::getMessage).collect(Collectors.joining("\r\n")));
    }
  }
}
