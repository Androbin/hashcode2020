import java.util.*;
import java.util.stream.*;

public final class Solver {
	private Solver() {
	}

	private static <T> Stream<T> intersect(final Collection<T> l0, final Collection<T> l1) {
		return l0.stream().filter(l1::contains);
	}

	private static int libraryInterest(final Library library, final int days) {
		return library.books.stream().take(library.capacity * days).sum();
	}

	public static List<Output> solveGreedy(final List<Library> libraries, final int days) {
		final Map<String, List<Slide>> tags1 = new HashMap<>();

		for (final Slide slide : slides) {
			for (final String tag : slide.getTags()) {
				tags1.computeIfAbsent(tag, foo -> new ArrayList<>()).add(slide);
			}
		}

		final List<Slide> presentation = new ArrayList<>(slides.size());
		Slide last = null;

		while (!slides.isEmpty()) {
			final Slide next;

			if (last == null) {
				next = slides.get(0);
			} else {
				final List<Slide> reduced = last.getTags().stream().flatMap(tag -> tags1.get(tag).stream()).distinct()
						.collect(Collectors.toList());

				final Map<Slide, Integer> scores = new HashMap<>();

				for (final Slide slide : reduced) {
					scores.put(slide, libraryInterest(last, slide));
				}

				next = reduced.stream().max((a, b) -> Integer.compare(scores.get(a), scores.get(b)))
						.orElseGet(() -> slides.get(0));
			}

			slides.remove(next);
			presentation.add(next);
			last = next;

			for (final String tag : last.getTags()) {
				tags1.get(tag).remove(last);
			}
		}

		System.out.println(score(presentation));
		return presentation;
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