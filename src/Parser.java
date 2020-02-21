import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class Parser {
    private Parser() {
    }

    public static String toOutput(final List<Output> outputs) {
        final StringJoiner result = new StringJoiner("\n");
        result.add(String.valueOf(outputs.size()));

        for (final Output output : outputs) {
            result.add(output.toString());
        }

        return result.toString();
    }

    public static Input parse(final Stream<String> lineStream) {
        final Iterator<String> iterator = lineStream.iterator();

        final String[] data = iterator.next().split(" ");
        final List<Integer> bookScores = Arrays.stream(iterator.next().split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        final int numberOfLibraries = Integer.parseInt(data[1]);
        final int numberOfDays = Integer.parseInt(data[2]);

        final List<Library> libraries = new ArrayList<>();
        final List<Book> books = IntStream.range(0, bookScores.size())
                .mapToObj(index -> new Book(index, bookScores.get(index))).collect(Collectors.toList());

        for (int i = 0; i < numberOfLibraries; i++) {
            final String[] libraryData = iterator.next().split(" ");
            final int singUpTime = Integer.parseInt(libraryData[1]);
            final int shipAmountPerDay = Integer.parseInt(libraryData[2]);

            final List<Book> libraryBooks = Arrays.stream(iterator.next().split(" "))
                    .mapToInt(Integer::parseInt)
                    .mapToObj(books::get)
                    .sorted()
                    .collect(Collectors.toList());

            final Library library = new Library(i, singUpTime, shipAmountPerDay, libraryBooks);
            libraries.add(library);
            libraryBooks.forEach(book -> book.libraries.add(library));
        }

        Collections.sort(libraries);
        return new Input(numberOfDays, libraries);
    }
}