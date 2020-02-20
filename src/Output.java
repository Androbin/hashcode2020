import java.io.*;
import java.util.*;

public final class Output {
	public final Library library;
	public final List<Book> books;

	public Output(final Library library) {
		this.library = library;
		books = new ArrayList<Book>();
	}

	@Override
	public String toString() {
		final StringBuilder result = new StringBuilder();
		// library and num_books
		result.append(library).append(' ').append(books.size()).append('\n');

		// books in order
		for (final Book book : books) {
			result.append(book.toString()).append(' ');
		}
		result.append('\n');

		return result.toString();
	}
}