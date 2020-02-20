import java.util.stream.Stream;

public final class Parser {
	private Parser() {
	}

	public static Input parse(final Stream<String> lineStream) {
		return null;
    }

	public static String toOutput(final List<Output> outputs) {
		final StringBuilder result = new StringBuilder();
		result.append(outputs.size()).append('\n');

		for (final Output output : outputs) {
			result.append(output.toString()).append('\n');
		}

		return result.toString();
	}
}