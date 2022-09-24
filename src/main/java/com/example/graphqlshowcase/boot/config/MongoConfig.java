package com.example.graphqlshowcase.boot.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import java.util.Collection;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

  private @Value("${spring.data.mongodb.database}") String databaseName;
  private @Value("${spring.data.mongodb.host}") String host;
  private @Value("${spring.data.mongodb.port}") Integer port;
  private @Value("${spring.data.mongodb.username}") String username;
  private @Value("${spring.data.mongodb.password}") CharSequence password;

  @Override
  protected String getDatabaseName() {
    return databaseName;
  }

  @Override
  protected Collection<String> getMappingBasePackages() {
    return Collections.singleton("com.example.graphqlshowcase.adapter.out");
  }

  @Override
  public MongoClient mongoClient() {
    ConnectionString connectionString = new ConnectionString(connectionString());
    MongoClientSettings mongoClientSettings =
        MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .retryReads(true)
            .build();

    return MongoClients.create(mongoClientSettings);
  }

  private String connectionString() {
    if (username != null && password != null && !username.isBlank() && !password.isEmpty()) {
      return "mongodb://%s:%s@%s:%d/".formatted(username, password, host, port);
    }
    return "mongodb://%s:%d".formatted(host, port);
  }
}
