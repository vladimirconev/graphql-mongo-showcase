package com.example.graphqlshowcase.boot.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import java.util.Collection;
import java.util.Collections;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

  @Override
  protected String getDatabaseName() {
    return "books";
  }

  @Override
  protected Collection<String> getMappingBasePackages() {
    return Collections.singleton("com.example.graphqlshowcase.adapter.out");
  }

  @Override
  public MongoClient mongoClient() {
    ConnectionString connectionString =
        new ConnectionString("mongodb://root:example@localhost:27017/");
    MongoClientSettings mongoClientSettings =
        MongoClientSettings.builder().applyConnectionString(connectionString).build();

    return MongoClients.create(mongoClientSettings);
  }
}
