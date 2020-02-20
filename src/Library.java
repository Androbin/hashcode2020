import java.util.List;

public final class Library {
	public final int id;
	public final int signupTime;
	public final int shipAmount;
	public final List<Book> books;

	public Library(final int id, final int signupTime, final int shipAmount, final List<Book> books) {
		this.id = id;
		this.signupTime = signupTime;
		this.shipAmount = shipAmount;
		this.books = books;
	}

	@Override
	public String toString() {
		return String.valueOf(id);
	}
}