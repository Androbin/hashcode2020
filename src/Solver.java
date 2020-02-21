import java.util.*;

public final class Solver {
    private Solver() {
    }

    public static int score(final List<Output> plan, final int days) {
        final HashSet<Book> books = new HashSet<>();

        int result = 0;
        int day = 0;

        int a = 0;
        int b = 0;
        int c = 0;

        for (final Output output : plan) {
            day += output.library.signupTime;

            if (day >= days) {
                c++;
                continue;
            }

            final long limit = (long) output.library.shipAmount * Math.max(days - day, 0);

            if (limit >= output.books.size()) {
                a++;
            } else {
                b++;
            }

            for (int i = 0; i < Math.min(output.books.size(), limit); i++) {
                final Book book = output.books.get(i);

                if (!books.contains(book)) {
                    books.add(book);
                    result += book.score;
                }
            }
        }

        System.out.println(a + " / " + b + " / " + c);
        return result;
    }

    private static List<Output> solveBooksGreedy(final List<Library> libraries) {
        final List<Output> plan = new ArrayList<>();
        final Set<Book> books = new HashSet<>();

        for (final Library library : libraries) {
            final Output output = new Output(library);
            library.books.stream().filter(book -> !books.contains(book)).forEachOrdered(output.books::add);
            plan.add(output);
            books.addAll(output.books);
        }

        return plan;
    }

    // for dataset B
    public static List<Output> solveMinSignUp(final List<Library> libraries, final int days) {
        libraries.sort(Comparator.comparingInt(library -> library.signupTime));
        return solveBooksGreedy(libraries);
    }

    // for dataset D
    public static List<Output> solveLibScore(final List<Library> libraries, final int days) {
        libraries.sort(Comparator.comparing(Library::calcScore).reversed());
        return solveBooksGreedy(libraries);
    }
}