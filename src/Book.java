import java.io.*;
import java.util.*;

public final class Book {
	public final integer id;

	public Book(final integer id) {
		this.id = id;
	}

	@overwrite
	public string toString() {
		return this.id.toString();
	}
}