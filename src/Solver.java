import java.util.*;
import java.util.stream.Collectors;

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

    private static List<Output> solveBooksGreedy(final List<Library> libraries, final int days) {
        final List<Output> plan = new ArrayList<>();
        final Set<Book> books = new HashSet<>();
        int day = 0;

        for (final Library library : libraries) {
            day += library.signupTime;

            final Output output = new Output(library);
            library.books.stream()
                    .filter(book -> !books.contains(book))
                    .limit((long) library.shipAmount * Math.max(days - day, 0))
                    .forEachOrdered(output.books::add);
            plan.add(output);
            books.addAll(output.books);
        }

        return plan;
    }

    // best solution for D, E, F
    public static List<Output> solveTimeGreedy(final List<Library> libraries, final int days) {
        final List<Output> plan = new ArrayList<>();
        int day = 0;

        while (plan.size() < libraries.size()) {
            final int day_ = day;
            libraries.sort(Comparator.comparingInt((Library library) -> {
                // C -> library.signupTime * 10
                final int limit = days - day_ - library.signupTime;
                return library.calcSum()[Math.max(0, Math.min(limit, library.books.size()))];
            }).reversed());
            final Library bestLibrary = libraries.get(0);
            libraries.remove(bestLibrary);
            final Output output = new Output(bestLibrary);
            plan.add(output);
            day += bestLibrary.signupTime;

            final List<Book> bestBooks = bestLibrary.books.stream()
                    .limit((long) bestLibrary.shipAmount * Math.max(days - day - bestLibrary.signupTime, 0))
                    .collect(Collectors.toList());

            for (final Book book : bestBooks) {
                output.books.add(book);
                book.libraries.forEach(library -> {
                    if (library != output.library) {
                        library.books.remove(book);
                    }

                    // library.invalidateSum();
                });
            }
        }

        return plan;
    }

    // best solution for A, B, C
    public static List<Output> solveMinSignUp(final List<Library> libraries, final int days) {
        libraries.sort(Comparator.comparingInt(library -> library.signupTime));
        return solveBooksGreedy(libraries, days);
    }
}