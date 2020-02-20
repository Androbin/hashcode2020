import java.util.*;

public final class Solver {
    private Solver() {
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

    public static int score(final List<Output> plan, final int days) {
        final HashSet<Book> books = new HashSet<>();

        int result = 0;
        int day = 0;

        for (final Output output : plan) {
            day += output.library.signupTime;

            if (day >= days) {
                break;
            }

            final long limit = (long) output.library.shipAmount * Math.max(days - day, 0);

            for (int i = 0; i < Math.min(output.books.size(), limit); i++) {
                final Book book = output.books.get(i);

                if (!books.contains(book)) {
                    books.add(book);
                    result += book.score;
                }
            }
        }

        return result;
    }

    // for dataset B
    public static List<Output> solveMinSignUp(final List<Library> libraries, final int days) {
        final List<Library> registration = registration(libraries, days);
        registration.sort(Comparator.comparing(library -> library.signupTime));

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

    // for dataset D
    public static List<Output> solveMaxBooksNum(final List<Library> libraries, final int days) {
        final List<Library> registration = registration(libraries, days);
        registration.sort(Comparator.comparing((Library library) -> library.books.size()).reversed());

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

    // for dataset D
    public static List<Output> solveMaxBooksNumAndRemoveCommittedBooks(final List<Library> libraries, final int days) {
        final List<Library> registration = new ArrayList<>(libraries);
        final List<Output> plan = new ArrayList<>();

        // sort by num of books desc
        registration.sort(Comparator.comparing((Library library)->library.books.size()).reversed());

        while (!registration.isEmpty()) {
            Library library = registration.remove(0);
            final Output output = new Output(library);

            // add books to output
            library.books.stream().forEachOrdered(output.books::add);

            // remove added books from all other libraries
            for (Library otherLib : registration) {
                otherLib.books.removeIf(book -> library.books.contains(book));
            }

            // sort by num of books desc
            registration.sort(Comparator.comparing((Library lib)->lib.books.size()).reversed());

            plan.add(output);
        }

        return plan;
    }

    public static List<Output> solveLibScore(final List<Library> libraries, final int days) {
        final List<Library> registration = registration(libraries, days);
        registration.sort(Comparator.comparing((Library library) -> library.calcScore()).reversed());

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