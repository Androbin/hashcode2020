import java.io.*;
import java.util.*;

public final class Book {
	public final integer id;
	public final integer score;

	public Book(final integer id, final integer score) {
		this.id = id;
		this.score = score;
	}

	@overwrite
	public string toString() {
		return this.id.toString();
	}
}