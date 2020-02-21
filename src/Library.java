import java.util.List;

public final class Library implements Comparable<Library> {
    public final int id;
    public final int signupTime;
    public final int shipAmount;
    public final List<Book> books;
    public int[] sums;

    public Library(final int id, final int signupTime, final int shipAmount, final List<Book> books) {
        this.id = id;
        this.signupTime = signupTime;
        this.shipAmount = shipAmount;
        this.books = books;
    }

    public int[] calcSum() {
        if (sums != null) {
            return sums;
        }

        sums = new int[books.size() + 1];
        sums[0] = 0;

        for (int i = 0; i < books.size(); i++) {
            sums[i + 1] = sums[i] + books.get(i).score;
        }

        return sums;
    }

    public void invalidateSum() {
        sums = null;
    }

    @Override
    public int compareTo(final Library library) {
        return Integer.compare(signupTime, library.signupTime);
    }

    public int calcScore() {
        int score = 0;

        for (final Book book : books) {
            score += book.score;
        }

        return score;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}