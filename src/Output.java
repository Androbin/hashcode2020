import java.util.ArrayList;
import java.util.List;

public final class Output {
    public final Library library;
    public final List<Book> books;

    public Output(final Library library) {
        this.library = library;
        books = new ArrayList<>();
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();

        // library and num_books
        result.append(library);
        result.append(' ');
        result.append(books.size());
        result.append('\n');

        // books in order
        for (final Book book : books) {
            result.append(book.toString()).append(' ');
        }

        result.append('\n');
        return result.toString();
    }
}