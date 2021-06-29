package com.example.graphqlshowcase.boot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.graphqlshowcase.adapter.in.RestExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class ErrorHandlingConfig {

	@Bean
	public RestExceptionHandler exceptionHandler(@Autowired  DefaultErrorAttributes errorAttributes) {
		return new RestExceptionHandler(errorAttributes, new ObjectMapper());
	}

}
