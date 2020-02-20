import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Parser {
    private Parser() {
    }

	public static String toOutput(final List<Output> outputs) {
		final StringBuilder result = new StringBuilder();
		result.append(outputs.size()).append('\n');

		for (final Output output : outputs) {
			result.append(output.toString()).append('\n');
		}

		return result.toString();
	}

	public static String toDebugOutput(final List<Output> outputs) {
		final StringBuilder result = new StringBuilder();
		result.append(outputs.size()).append('\n');

		for (final Output output : outputs) {
			result.append(output.toDebugString()).append('\n');
		}

		return result.toString();
	}

	public static Input parse(final Stream<String> lineStream) {
		final Iterator<String> iterator = lineStream.iterator();

		final String[] data = iterator.next().split(" ");
		final int numberOfLibraries = Integer.parseInt(data[1]);
		final int numberOfDays = Integer.parseInt(data[2]);

		final List<Integer> bookScores = Arrays.stream(iterator.next().split(" "))
				                                 .map(Integer::parseInt)
				                                 .collect(Collectors.toList());
		final List<Library> libraries = new ArrayList<>();

		for (int i = 0; i < numberOfLibraries; i++) {
			final String[] libraryData = iterator.next().split(" ");
			final List<Book> libraryBooks = Arrays.stream(iterator.next().split(" "))
					                                .map(Integer::parseInt)
					                                .map(index -> new Book(index, bookScores.get(index))).collect(Collectors.toList());

			final int singUpTime = Integer.parseInt(libraryData[1]);
			final int shipAmountPerDay = Integer.parseInt(libraryData[2]);


			Collections.sort(libraryBooks, Comparator.<Book>comparingInt(b -> b.score).reversed());
			libraries.add(new Library(i, singUpTime, shipAmountPerDay, libraryBooks));
		}

		return new Input(numberOfDays, libraries);
	}
}