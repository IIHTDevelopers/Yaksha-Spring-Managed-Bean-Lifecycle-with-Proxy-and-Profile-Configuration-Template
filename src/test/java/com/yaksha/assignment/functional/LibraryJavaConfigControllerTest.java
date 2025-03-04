package com.yaksha.assignment.functional;

import static com.yaksha.assignment.utils.TestUtils.businessTestFile;
import static com.yaksha.assignment.utils.TestUtils.currentTest;
import static com.yaksha.assignment.utils.TestUtils.testReport;
import static com.yaksha.assignment.utils.TestUtils.yakshaAssert;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.yaksha.assignment.config.AppConfig;
import com.yaksha.assignment.models.Book;
import com.yaksha.assignment.models.Library;
import com.yaksha.assignment.proxy.LibraryServiceProxy;

public class LibraryJavaConfigControllerTest {

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	// Test to verify if the application context loads beans correctly
	@Test
	public void testApplicationContextLoads() throws IOException {
		// Load the context using Java-based configuration
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		// Retrieve beans from the context
		Book book = context.getBean(Book.class);
		Library library = context.getBean(Library.class);

		// Assert that beans are created
		boolean bookLoaded = book != null;
		boolean libraryLoaded = library != null;

		// Log the checks
		System.out.println("Book bean loaded: " + bookLoaded);
		System.out.println("Library bean loaded: " + libraryLoaded);

		// Auto-grading with yakshaAssert
		yakshaAssert(currentTest(), bookLoaded && libraryLoaded, businessTestFile);
	}

	// Test for @PostConstruct and @PreDestroy annotations
	@Test
	public void testPostConstructAndPreDestroy() throws IOException {
		// Load the context using Java-based configuration
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		// Retrieve the Library bean
		Library library = context.getBean(Library.class);

		// Check if @PostConstruct annotation is present in the Library class
		boolean postConstructPresent = false;
		boolean preDestroyPresent = false;

		// Check all methods of the Library class for @PostConstruct and @PreDestroy
		// annotations
		Method[] methods = Library.class.getDeclaredMethods();
		for (Method method : methods) {
			if (method.isAnnotationPresent(PostConstruct.class)) {
				postConstructPresent = true;
			}
			if (method.isAnnotationPresent(PreDestroy.class)) {
				preDestroyPresent = true;
			}
		}

		// Log the result of the checks
		System.out.println("Is @PostConstruct present in Library class? " + postConstructPresent);
		System.out.println("Is @PreDestroy present in Library class? " + preDestroyPresent);

		// Assert that @PostConstruct and @PreDestroy annotations are present in the
		// Library class
		boolean lifecycleAnnotationsPresent = postConstructPresent && preDestroyPresent;

		// Auto-grading with yakshaAssert
		yakshaAssert(currentTest(), lifecycleAnnotationsPresent, businessTestFile);

		// Close the context to trigger the destroy method
		context.close();
	}

	// Test to verify if the proxy method works for performLibraryAction()
	@Test
	public void testLibraryProxy() throws IOException {
		// Load the context using Java-based configuration
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		// Mock the Library class to check if issueBook is called
		Library library = mock(Library.class);
		LibraryServiceProxy proxy = context.getBean(LibraryServiceProxy.class);

		// Create a book and add it to the mocked library
		Book book = new Book("Java Basics", 50);
		library.addBook(book);

		// Perform library action via proxy
		proxy.performLibraryAction(library); // This will trigger the proxy logic

		// Verify that the issueBook method was called on the Library mock
		verify(library, times(1)).issueBook(); // Verifying that issueBook() is called once

		// Log the result
		System.out.println("Library action performed via proxy and issueBook was called.");

		// Auto-grading with yakshaAssert
		yakshaAssert(currentTest(), true, businessTestFile);

		// Close the context to trigger the destroy method
		context.close();
	}

	// Test to check if the library method issues the book correctly
	@Test
	public void testIssueBookMethod() throws IOException {
		// Load the context using Java-based configuration
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		// Retrieve beans from the context
		Library library = context.getBean(Library.class);

		// Add a book to the library
		Book book = new Book("Java Basics", 50);
		library.addBook(book);

		// Issue the book
		library.issueBook();

		// Assert the book quantity has decreased
		boolean quantityDecreased = book.getQuantity() == 49;

		// Log the result
		System.out.println("Book issued: " + book.getName() + " | Remaining Quantity: " + book.getQuantity());

		// Auto-grading with yakshaAssert
		yakshaAssert(currentTest(), quantityDecreased, businessTestFile);

		// Close the context to trigger the destroy method
		context.close();
	}
}
