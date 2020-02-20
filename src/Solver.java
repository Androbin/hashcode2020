import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public final class Solver {
    private Solver() {
    }

    private static <T> Stream<T> intersect(final Collection<T> l0, final Collection<T> l1) {
        return l0.stream().filter(l1::contains);
    }

    private static int libraryInterest(final Library library, final int days) {
        return library.books.stream().limit(library.shipAmount * days).mapToInt(book -> book.score).sum();
    }

    public static List<Output> solveGreedy(final List<Library> libraries, final int days) {
        final List<Library> registration = new ArrayList<>();
        registration.addAll(libraries);

        for (int i = 0; i < days; i++) {
            int day = 0;

            for (int j = 0; j < libraries.size() - 1; j++) {
                final Library a = registration.get(j);
                final Library b = registration.get(j + 1);

                final int scoreA1 = libraryInterest(a, days - day - a.signupTime);
                final int scoreB1 = libraryInterest(b, days - day - a.signupTime - b.signupTime);

                final int scoreA2 = libraryInterest(a, days - day - b.signupTime - a.signupTime);
                final int scoreB2 = libraryInterest(b, days - day - b.signupTime);

                if (scoreA2 + scoreB2 > scoreA1 + scoreB1) {
                    Collections.swap(registration, j, j + 1);
                    day += b.signupTime;
                } else {
                    day += a.signupTime;
                }
            }
        }

        final List<Output> plan = new ArrayList<>();
        return plan;
    }

    public static int score(final List<Output> plan, final int days) {
        int day = 0;
        int result = 0;

        for (final Output output : plan) {
            day += output.library.signupTime;

            for (final Book book : output.books) {
                result += book.score;
            }
        }

        return result;
    }
}