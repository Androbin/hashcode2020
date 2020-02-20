public final class Book {
    public final int id;
    public final int score;

    public Book(final int id, final int score) {
        this.id = id;
        this.score = score;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}