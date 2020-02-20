import java.util.*;
import java.util.stream.Stream;

public final class Solver {
    private Solver() {
    }

    private static <T> Stream<T> intersect(final Collection<T> l0, final Collection<T> l1) {
        return l0.stream().filter(l1::contains);
    }

    private static int libraryInterest(final Library library, final int days) {
        final long limit = (long) library.shipAmount * Math.max(days, 0);
        return library.books.stream().limit(limit).mapToInt(book -> book.score).sum();
    }

    public static List<Library> registration(final List<Library> libraries, final int days) {
        final List<Library> registration = new ArrayList<>(libraries);
        boolean done = false;

        while (!done) {
            int day = 0;
            done = true;

            for (int i = 0; i < libraries.size() - 1; i++) {
                final Library a = registration.get(i);
                final Library b = registration.get(i + 1);

                if (day >= days) {
                    break;
                }

                final int scoreA1 = libraryInterest(a, days - day - a.signupTime);
                final int scoreB1 = libraryInterest(b, days - day - a.signupTime - b.signupTime);

                final int scoreA2 = libraryInterest(a, days - day - b.signupTime - a.signupTime);
                final int scoreB2 = libraryInterest(b, days - day - b.signupTime);

                if (scoreA2 + scoreB2 > scoreA1 + scoreB1) {
                    Collections.swap(registration, i, i + 1);
                    day += b.signupTime;
                    done = false;
                } else {
                    day += a.signupTime;
                }
            }
        }

        return registration;
    }

    public static List<Output> solveGreedy(final List<Library> libraries, final int days) {
        final List<Library> registration = registration(libraries, days);
        final List<Output> plan = new ArrayList<>();

        for (final Library library : registration) {
            final Output output = new Output(library);
            final long limit = (long) library.shipAmount * Math.max(days, 0);
            library.books.stream().limit(limit).forEachOrdered(output.books::add);
            plan.add(output);
        }

        return plan;
    }

    public static int score(final List<Output> plan, final int days) {
        int day = 0;
        int result = 0;

        for (final Output output : plan) {
            day += output.library.signupTime;

            // TODO: don't ignore deadline and speed

            for (final Book book : output.books) {
                result += book.score;
            }
        }

        return result;
    }

    // for dataset B
    public static List<Output> solveMinSignup(final List<Library> libraries, final int days) {

        libraries.sort(Comparator.comparing(library->library.signupTime));
        //System.out.println("librarys.signupTime: "+ libraries);
        final List<Output> plan = new ArrayList<>();

        for (final Library library : libraries) {
            final Output output = new Output(library);
            final long limit = (long) library.shipAmount * Math.max(days, 0);
            library.books.stream().limit(limit).forEachOrdered(output.books::add);
            plan.add(output);
        }

        return plan;
    }

}