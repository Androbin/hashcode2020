import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class SolverSanity {
    private SolverSanity() {
    }

    public static boolean sanity(final List<Output> outputs) {
        outputs.removeIf(output -> output.books.isEmpty());

        final Set<Library> libraries = new HashSet<>();

        for (final Output output : outputs) {
            if (!libraries.add(output.library)) {
                return false;
            }
            if (!output.library.allBooks.containsAll(output.books)) {
                return false;
            }
        }

        return true;
    }
}