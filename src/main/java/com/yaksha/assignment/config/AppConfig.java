package com.yaksha.assignment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yaksha.assignment.models.Book;
import com.yaksha.assignment.models.Library;
import com.yaksha.assignment.proxy.LibraryServiceProxy;

@Configuration
public class AppConfig {

	@Bean
	public Book book() {
		return new Book("Java Basics", 50);
	}

	@Bean
	public Library library() {
		return new Library("Central Library");
	}

	@Bean
	public LibraryServiceProxy libraryServiceProxy() {
		return new LibraryServiceProxy();
	}
}
