import java.util.ArrayList;
import java.util.List;

public final class Book implements Comparable<Book> {
    public final int id;
    public final int score;
    public final List<Library> libraries;

    public Book(final int id, final int score) {
        this.id = id;
        this.score = score;
        libraries = new ArrayList<>();
    }

    @Override
    public int compareTo(final Book book) {
        return -Integer.compare(score, book.score);
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}