import java.util.ArrayList;
import java.util.List;

public final class Library implements Comparable<Library> {
    public final int id;
    public final int signupTime;
    public final int shipAmount;
    public final List<Book> allBooks;
    public final List<Book> books;
    private int score = -1;

    public Library(final int id, final int signupTime, final int shipAmount, final List<Book> books) {
        this.id = id;
        this.signupTime = signupTime;
        this.shipAmount = shipAmount;
        this.allBooks = books;
        this.books = new ArrayList<>(books);
    }

    @Override
    public int compareTo(final Library library) {
        return Integer.compare(signupTime, library.signupTime);
    }

    public int calcScore() {
        if (score >= 0) {
            return score;
        }

        score = 0;

        for (final Book book : books) {
            score += book.score;
        }

        return score;
    }

    public void invalidateScore() {
        score = -1;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}