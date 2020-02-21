import java.util.ArrayList;
import java.util.List;

public final class Library implements Comparable<Library> {
    public final int id;
    public final int signupTime;
    public final int shipAmount;
    public final List<Book> allBooks;
    public final List<Book> books;
    public int score;

    public Library(final int id, final int signupTime, final int shipAmount, final List<Book> books) {
        this.id = id;
        this.signupTime = signupTime;
        this.shipAmount = shipAmount;
        this.allBooks = books;
        this.books = new ArrayList<>(books);

        for (final Book book : books) {
            score += book.score;
        }
    }

    @Override
    public int compareTo(final Library library) {
        return Integer.compare(signupTime, library.signupTime);
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}