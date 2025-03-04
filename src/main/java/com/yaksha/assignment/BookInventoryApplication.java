package com.yaksha.assignment;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.yaksha.assignment.config.AppConfig;
import com.yaksha.assignment.models.Book;
import com.yaksha.assignment.models.Library;
import com.yaksha.assignment.proxy.LibraryServiceProxy;

public class BookInventoryApplication {

	public static void main(String[] args) {
		// Create an application context based on AppConfig
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		// Retrieve beans from the context
		Book book = context.getBean(Book.class);
		Library library = context.getBean(Library.class);

		// Add book to library
		library.addBook(book);

		System.out.println("Book Info: " + book.getName() + ", Quantity: " + book.getQuantity());
		System.out.println("Library Info: " + library.getName());

		// Perform library action via proxy (issue the book)
		LibraryServiceProxy proxy = context.getBean(LibraryServiceProxy.class);
		proxy.performLibraryAction(library);

		// Verify the updated book quantity after issuing
		System.out.println("Updated Book Info: " + book.getName() + ", Quantity: " + book.getQuantity());

		// Close the context to trigger the destruction and @PreDestroy lifecycle method
		context.close();
	}
}
