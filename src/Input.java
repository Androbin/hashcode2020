import java.util.ArrayList;
import java.util.List;

public final class Input {
    public final int days;
    public final List<Library> libraries;

    public Input(final int days) {
        this.days = days;
        libraries = new ArrayList<>();
    }
}