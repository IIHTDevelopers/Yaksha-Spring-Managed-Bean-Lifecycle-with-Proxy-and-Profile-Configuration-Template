package com.yaksha.assignment.models;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class Library {

	private String name;
	private Book book; // Library can have one book for simplicity

	public Library(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	// This method will be invoked after the bean is created and all dependencies
	// are injected
	@PostConstruct
	public void init() {
		System.out.println("Library bean initialized: " + name);
		if (book != null) {
			System.out.println("Book initialized in library: " + book.getName());
		}
	}

	// This method will be invoked before the bean is destroyed
	@PreDestroy
	public void destroy() {
		System.out.println("Library bean destroyed: " + name);
		if (book != null) {
			System.out.println("Book destroyed: " + book.getName());
		}
	}

	// Add a book to the library
	public void addBook(Book book) {
		this.book = book;
		System.out.println("Book added to the library: " + book.getName());
	}

	// Issue the book
	public void issueBook() {
		if (this.book != null && this.book.getQuantity() > 0) {
			this.book.setQuantity(this.book.getQuantity() - 1);
			System.out.println("Book issued: " + this.book.getName());
		} else {
			System.out.println("Sorry, the book is not available for issuing.");
		}
	}

	// Method to demonstrate the proxy functionality
	public void performLibraryAction() {
		// Let's say this method represents the business logic, which the proxy will
		// wrap.
		System.out.println("Performing library action on: " + this.book.getName());
	}
}
