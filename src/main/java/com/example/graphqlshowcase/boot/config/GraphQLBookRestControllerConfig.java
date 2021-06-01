package com.example.graphqlshowcase.boot.config;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoOperations;

import com.example.graphqlshowcase.adapter.in.GraphQLBookRestController;
import com.example.graphqlshowcase.adapter.out.BookDataFetcher;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

@Configuration
public class GraphQLBookRestControllerConfig {

	@Autowired
	private MongoOperations mongoOperations;

	@Value("classpath:schema.graphqls")
	private Resource schemaResource;
	
	@Bean
	public GraphQL createGraphQlObject() throws IOException {
		File schemas = schemaResource.getFile();
		TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemas);
		RuntimeWiring wiring = RuntimeWiring.newRuntimeWiring().type("GetAllBooks",
				typeWiring -> typeWiring.dataFetcher("books", new BookDataFetcher(mongoOperations))).build();
		GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
		return new GraphQL.Builder(schema).build();
	}
	
	@Bean
	public GraphQLBookRestController graphQLBookRestController(final GraphQL graphQL) {
		return new GraphQLBookRestController(graphQL);
	}

}
