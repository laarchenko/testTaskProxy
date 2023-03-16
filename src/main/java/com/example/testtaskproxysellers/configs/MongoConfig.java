package com.example.testtaskproxysellers.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "com.example.testtaskproxysellers.repositories")
@Configuration
public class MongoConfig {
}
