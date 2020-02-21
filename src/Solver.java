import java.util.*;
import java.util.stream.Collectors;

public final class Solver {
    private Solver() {
    }

    public static int score(final List<Output> plan, final int days, final boolean debug) {
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

            if (limit >= output.library.allBooks.size()) {
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

        if (debug) {
            System.out.println(a + " / " + b + " / " + c);
        }

        return result;
    }

    private static List<Output> solveGreedy(final List<Library> libraries, final int days) {
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

    // compare must be fast
    private static List<Output> solveSpooky(final List<Library> libraries, final Comparator<Library> compare, final int days) {
        final List<Library> leftover = new ArrayList<>(libraries);
        final List<Output> plan = new ArrayList<>();
        int day = 0;

        while (plan.size() < libraries.size()) {
            leftover.sort(compare);
            final Library bestLibrary = leftover.remove(0);
            /*final Library bestLibrary = leftover.stream().min(compare).get();
            leftover.remove(bestLibrary);*/

            final Output output = new Output(bestLibrary);
            plan.add(output);
            day += bestLibrary.signupTime;

            final List<Book> bestBooks = bestLibrary.books.stream()
                    .limit((long) bestLibrary.shipAmount * Math.max(days - day, 0))
                    .collect(Collectors.toList());

            for (final Book book : bestBooks) {
                output.books.add(book);
                book.libraries.forEach(library -> {
                    if (library != output.library) {
                        library.books.remove(book);
                        library.score -= book.score;
                    }
                });
            }
        }

        return plan;
    }

    private static double intpow(final double x, final int n) {
        if (n < 0) {
            return 1.0 / intpow(x, -n);
        }

        double acc = 1.0;

        for (int i = 0; i < n; i++) {
            acc *= x;
        }

        return acc;
    }

    // best solution for A, B, C, D,  ,  ,
    public static List<Output> solveSpookyScore(final List<Library> libraries, final int days) {
        return solveSpooky(libraries, Comparator.comparingDouble((Library library) ->
                intpow(library.score, 7) * intpow(library.signupTime, -10)
        ).reversed(), days);
    }

    // best solution for A, B,  ,  , E, F,
    public static List<Output> solveRegression(final List<Library> libraries, final int days) {
        List<Output> bestPlan = null;
        int bestScore = 0;

        // only need some combinations
        for (int i = 7; i <= 11; i += 1) {
            for (int j = 0; j <= 7; j += 1) {
                final int a = i;
                final int b = j;

                final Comparator<Library> compare = Comparator.comparingDouble((Library library) -> {
                    return intpow(library.score, a) * intpow(library.shipAmount, b) * intpow(library.signupTime, -10);
                }).reversed();

                libraries.sort(compare);
                final List<Output> plan = solveGreedy(libraries, days);

                // final List<Output> plan = solveSpooky(libraries, compare, days);

                final int score = score(plan, days, false);

                if (score >= bestScore) {
                    bestPlan = plan;
                    bestScore = score;
                }
            }
        }

        return bestPlan;
    }

    public static List<Output> solveLibScoreBySignupAndShipAmount(final List<Library> libraries, final int days) {
        final List<Library> registration = registration(libraries, days);
        registration.sort(Comparator.comparing((Library library) -> library.signupTime/(library.calcScore()*library.shipAmount)));

        final List<Output> plan = new ArrayList<>();
        final Set<Book> books = new HashSet<>();

        for (final Library library : registration) {
            final Output output = new Output(library);
            library.books.stream().filter(book -> !books.contains(book)).forEachOrdered(output.books::add);
            plan.add(output);
            books.addAll(output.books);
        }

        return plan;
    }

}