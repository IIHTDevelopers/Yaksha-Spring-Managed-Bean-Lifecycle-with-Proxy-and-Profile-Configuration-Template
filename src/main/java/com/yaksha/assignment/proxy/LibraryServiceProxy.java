package com.yaksha.assignment.proxy;

import com.yaksha.assignment.models.Library;

public class LibraryServiceProxy {

	// Here we are simulating a proxy for the Library method
	public void performLibraryAction(Library library) {
		System.out.println("Proxy: Before calling the actual method.");

		// You can add additional logic like logging, auditing, transaction management,
		// etc.
		library.issueBook();

		System.out.println("Proxy: After calling the actual method.");
	}
}
