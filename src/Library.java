import java.util.*;

public final class Library {
	public final Integer id;
	public final List<Book> books;

	public Library(final Integer id) {
		this.id = id;
		books = new ArrayList<Book>();
	}

	@Override
	public String toString() {
		return this.id.toString();
	}
}